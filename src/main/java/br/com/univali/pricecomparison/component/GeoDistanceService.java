package br.com.univali.pricecomparison.component;

import org.springframework.stereotype.Service;

import br.com.univali.pricecomparison.component.dto.LatLonBoxDTO;

@Service
public class GeoDistanceService {
	
	public LatLonBoxDTO maxLatLonBox(Double lat, Double lon, Double distance) {
		return new LatLonBoxDTO(
				nextPoint(lat, lon, 180.0, distance)[0], 
				nextPoint(lat, lon, 0.0, distance)[0], 
				nextPoint(lat, lon, 270.0, distance)[1], 
				nextPoint(lat, lon, 90.0, distance)[1]
			);
	}

	private static Double[] nextPoint(Double latA, Double lonA, Double direcao, Double distancia)
    {

        Double α1 = ToRadians(direcao);
        Double φ1 = ToRadians(latA);
        Double λ1 = ToRadians(lonA);
        Double s = distancia;

		Double a = 6378137.0, b = 6356752.314245, f = 1 / 298.257223563; // WGS-84 ellipsoid params

		Double sinα1 = (Double) Math.sin((double) α1);
		Double cosα1 = (Double) Math.cos((double) α1);

		Double tanU1 = (1 - f) * (Double) Math.tan((double) φ1);
		Double cosU1 = 1 / (Double) Math.sqrt((double) (1 + tanU1 * tanU1));
        Double sinU1 = tanU1 * cosU1;
		Double σ1 = (Double) Math.atan2((double) tanU1, (double) cosα1);
        Double sinα = cosU1 * sinα1;
        Double cosSqα = 1 - sinα * sinα;
        Double uSq = cosSqα * (a * a - b * b) / (b * b);
        Double A = 1 + uSq / 16384 * (4096 + uSq * (-768 + uSq * (320 - 175 * uSq)));
        Double B = uSq / 1024 * (256 + uSq * (-128 + uSq * (74 - 47 * uSq)));

        Double σ = s / (b * A), σʹ;
        Double cos2σM;
        Double sinσ;
        Double cosσ;
        Double Δσ;
        do
        {
            cos2σM = (Double)Math.cos((double)(2 * σ1 + σ));
            sinσ = (Double)Math.sin((double)σ);
            cosσ = (Double)Math.cos((double)σ);
            Δσ = B * sinσ * (cos2σM + B / 4 * (cosσ * (-1 + 2 * cos2σM * cos2σM) -
                B / 6 * cos2σM * (-3 + 4 * sinσ * sinσ) * (-3 + 4 * cos2σM * cos2σM)));
            σʹ = σ;
            σ = s / (b * A) + Δσ;
        } while (Math.abs((double)(σ - σʹ)) > 1e-12);

        Double tmp = sinU1 * sinσ - cosU1 * cosσ * cosα1;
        Double φ2 = (Double)Math.atan2((double)(sinU1 * cosσ + cosU1 * sinσ * cosα1), (double)((1 - f) * (Double)Math.sqrt((double)(sinα * sinα + tmp * tmp))));
        Double λ = (Double)Math.atan2((double)(sinσ * sinα1), (double)(cosU1 * cosσ - sinU1 * sinσ * cosα1));
        Double C = f / 16 * cosSqα * (4 + f * (4 - 3 * cosSqα));
        Double L = λ - (1 - C) * f * sinα *
            (σ + C * sinσ * (cos2σM + C * cosσ * (-1 + 2 * cos2σM * cos2σM)));
        Double λ2 = (λ1 + L + 3 * (Double)Math.PI) % (2 * (Double)Math.PI) - (Double)Math.PI;  // normalise to -180...+180

        Double latDest = ToDegrees(φ2);
        Double longDest = ToDegrees(λ2);

		return new Double[] { latDest, longDest };
    }

	private static Double ToDegrees(Double radians) {
		return radians * 180 / (Double) Math.PI;
	}

	private static Double ToRadians(Double val) {
		return ((Double) Math.PI / 180) * val;
	}

	/**
	 * retorna a distancia calculada pela formula de vincent, de maior precisao
	 * 
	 * @param lat1
	 * @param lon1
	 * @param lat2
	 * @param lon2
	 * @return distancia em metros entre os dois pontos
	 */
	public Double distance(double lat1, double lon1, double lat2, double lon2) {
		if (lon1 == -180)
			lon1 = 180;
		double φ1 = (double) ToRadians(lat1), λ1 = (double) ToRadians(lon1);
		double φ2 = (double) ToRadians(lat2), λ2 = (double) ToRadians(lon2);

		double a = 6378137, b = 6356752.314245, f = 1 / 298.257223563; // WGS-84 ellipsoid params

		double L = λ2 - λ1;
		double tanU1 = (1 - f) * Math.tan(φ1), cosU1 = 1 / Math.sqrt((1 + tanU1 * tanU1)), sinU1 = tanU1 * cosU1;
		double tanU2 = (1 - f) * Math.tan(φ2), cosU2 = 1 / Math.sqrt((1 + tanU2 * tanU2)), sinU2 = tanU2 * cosU2;

		double sinλ, cosλ, sinSqσ, sinσ = 0, cosσ = 0, σ = 0, sinα, cosSqα = 0, cos2σM = 0, C;

		double λ = L, λʹ, iterations = 0;
		Boolean antimeridian = Math.abs(L) > Math.PI;
		do {
			sinλ = Math.sin(λ);
			cosλ = Math.cos(λ);
			sinSqσ = (cosU2 * sinλ) * (cosU2 * sinλ)
					+ (cosU1 * sinU2 - sinU1 * cosU2 * cosλ) * (cosU1 * sinU2 - sinU1 * cosU2 * cosλ);
			if (sinSqσ == 0)
				break; // co-incident points
			sinσ = Math.sqrt(sinSqσ);
			cosσ = sinU1 * sinU2 + cosU1 * cosU2 * cosλ;
			σ = Math.atan2(sinσ, cosσ);
			sinα = cosU1 * cosU2 * sinλ / sinσ;
			cosSqα = 1 - sinα * sinα;
			cos2σM = (cosSqα != 0) ? (cosσ - 2 * sinU1 * sinU2 / cosSqα) : 0; // equatorial line: cosSqα=0 (§6)
			C = f / 16 * cosSqα * (4 + f * (4 - 3 * cosSqα));
			λʹ = λ;
			λ = L + (1 - C) * f * sinα * (σ + C * sinσ * (cos2σM + C * cosσ * (-1 + 2 * cos2σM * cos2σM)));
			double iterationCheck = antimeridian ? Math.abs(λ) - Math.PI : Math.abs(λ);
			if (iterationCheck > Math.PI)
				throw new RuntimeException("λ > π");
		} while (Math.abs(λ - λʹ) > 1e-12 && ++iterations < 1000);
		if (iterations >= 1000)
			throw new RuntimeException("Formula failed to converge");

		double uSq = cosSqα * (a * a - b * b) / (b * b);
		double A = 1 + uSq / 16384 * (4096 + uSq * (-768 + uSq * (320 - 175 * uSq)));
		double B = uSq / 1024 * (256 + uSq * (-128 + uSq * (74 - 47 * uSq)));
		double Δσ = B * sinσ * (cos2σM + B / 4 * (cosσ * (-1 + 2 * cos2σM * cos2σM)
				- B / 6 * cos2σM * (-3 + 4 * sinσ * sinσ) * (-3 + 4 * cos2σM * cos2σM)));

		double distance = b * A * (σ - Δσ);

		return distance;
	}
}
