package com.github.fedy2.weather;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.xml.bind.JAXBException;

import com.github.fedy2.weather.YahooWeatherService.LimitDeclaration;
import com.github.fedy2.weather.data.Astronomy;
import com.github.fedy2.weather.data.Atmosphere;
import com.github.fedy2.weather.data.Channel;
import com.github.fedy2.weather.data.Condition;
import com.github.fedy2.weather.data.Forecast;
import com.github.fedy2.weather.data.Item;
import com.github.fedy2.weather.data.Units;
import com.github.fedy2.weather.data.Wind;
import com.github.fedy2.weather.data.unit.BarometricPressureState;
import com.github.fedy2.weather.data.unit.DegreeUnit;
import com.github.fedy2.weather.data.unit.DistanceUnit;
import com.github.fedy2.weather.data.unit.PressureUnit;
import com.github.fedy2.weather.data.unit.SpeedUnit;
import com.github.fedy2.weather.data.unit.Time;
import com.github.fedy2.weather.data.unit.WeekDay;

public class YahooWeatherAPI {

	public static void callAPI() {
		try {
			YahooWeatherService service = new YahooWeatherService();
			/*
			 * FAHRENHEIT
			 * CELSIUS
			 */
			LimitDeclaration LimitDeclaration = service.getForecastForLocation("boston", DegreeUnit.CELSIUS);
			Channel channel = service.getForecast("55812231", DegreeUnit.FAHRENHEIT);
			channel.getTitle();
			Astronomy astronomy = channel.getAstronomy();
			if (astronomy != null) {			
				Time sunriseTime = astronomy.getSunrise();
				Time sunsetTime = astronomy.getSunset();
				
				if (sunriseTime != null) {
					String sunrise = sunriseTime.getHours() + ":" + sunriseTime.getMinutes() + " " + sunriseTime.getConvention().toString();
					System.out.println("sunrise: "+sunrise);
				}
				if (sunsetTime != null) {
					String sunset = sunsetTime.getHours() + ":" + sunsetTime.getMinutes() + " " + sunsetTime.getConvention().toString();
					System.out.println("sunset:　"+sunset);
				}
			}
			Units units = channel.getUnits();
			DistanceUnit distanceUnit = units.getDistance();
			PressureUnit pressureUnit = units.getPressure();
			SpeedUnit speedUnit = units.getSpeed();
			DegreeUnit degreeUnit = units.getTemperature();
			
			Atmosphere atmosphere = channel.getAtmosphere();
			if (atmosphere != null) {
				Integer humidity = atmosphere.getHumidity();
				Float visibility = atmosphere.getVisibility();	
				Float pressure = atmosphere.getPressure();
				BarometricPressureState rising = atmosphere.getRising();
				if (humidity != null) {
					System.out.println("humidity: "+humidity + "%");
				}
				if (visibility != null) {
					System.out.println("visibility: "+visibility + " " + distanceUnit);
				}
				if (pressure != null) {
					System.out.println("pressure: "+pressure+ " " + pressureUnit);
				}
				if (rising != null) {
					System.out.println("Barometric pressure states.: " + rising);
				}
			}
			Item item = channel.getItem();
			if (item != null) {
				Condition condition = item.getCondition();
				if (condition != null) {
					condition.getCode();
					condition.getDate();
					condition.getTemp();
					condition.getText();
				}
				
				String description = item.getDescription();
	//			if (description != null)
	//				System.out.println("description: "+description);
				
				List<Forecast> forecastList = item.getForecasts();
				if (forecastList != null) {
					CodeInfo ci = new CodeInfo();
					System.out.println("Forecast List:");
					for (Forecast forecast: forecastList) {
						int code = forecast.getCode();
						Date date = forecast.getDate();
						WeekDay day = forecast.getDay();
						int high = forecast.getHigh();
						int low = forecast.getLow();
						String text = forecast.getText();
						String weatherInfo = CodeInfo.codeMap.get(code);
						System.out.println("code: "+code + " "+weatherInfo);
						System.out.println("date: "+date);
						System.out.println("day: "+day);
						System.out.println("high: "+high);
						System.out.println("low: "+low);
						System.out.println("text: "+text);
						System.out.println("----------------------");
					}
				}
				
				item.getGeoLat();
				item.getGeoLong();
				item.getGuid();
				item.getLink();
				item.getPubDate();
				item.getTitle();
			}
			
			Wind wind = channel.getWind();
			if (wind != null) {
				int chill = wind.getChill();
				int direction = wind.getDirection();
				float speed = wind.getSpeed();
			}
			
			System.out.println(channel.getDescription());
		} catch (Exception e) {
			e.getMessage();
		}
	}
	
	public static void main(String[] args) throws JAXBException, IOException {
		YahooWeatherAPI.callAPI();
	}

}
class CodeInfo {
	public static final HashMap<Integer, String> codeMap = new HashMap<Integer, String>();

	public CodeInfo() {
		codeMap.put(0,"龍捲風");
		codeMap.put(1, "熱帶風暴");
		codeMap.put(2, "颶風");
		codeMap.put(3, "強雷陣雨");
		codeMap.put(4, "雷陣雨");
		codeMap.put(5, "混合雨雪");
		codeMap.put(6, "混合雨雪");
		codeMap.put(7, "混合雨雪");
		codeMap.put(8, "冰凍小雨");
		codeMap.put(9, "細雨");
		codeMap.put(10, "凍雨");
		codeMap.put(11, "陣雨");
		codeMap.put(12, "陣雨");
		codeMap.put(13, "飄雪");
		codeMap.put(14, "陣雪");
		codeMap.put(15, "吹雪");
		codeMap.put(16, "下雪");
		codeMap.put(17, "冰雹");
		codeMap.put(18, "雨雪");
		codeMap.put(19, "多塵");
		codeMap.put(20, "多霧");
		codeMap.put(21, "陰霾");
		codeMap.put(22, "多煙");
		codeMap.put(23, "狂風大作");
		codeMap.put(24, "有風");
		codeMap.put(25, "冷");
		codeMap.put(26, "多雲");
		codeMap.put(27, "晴間多雲（夜）");
		codeMap.put(28, "晴間多雲（日）");
		codeMap.put(29, "晴間多雲（夜）");
		codeMap.put(30, "晴間多雲（日）");
		codeMap.put(31, "清晰的（夜）");
		codeMap.put(32, "晴朗");
		codeMap.put(33, "晴朗（夜）");
		codeMap.put(34, "晴朗（日）");
		codeMap.put(35, "雨和冰雹");
		codeMap.put(36, "炎熱");
		codeMap.put(37, "雷陣雨");
		codeMap.put(38, "零星雷陣雨");
		codeMap.put(39, "零星雷陣雨");
		codeMap.put(40, "零星雷陣雨");
		codeMap.put(41, "大雪");
		codeMap.put(42, "零星陣雪");
		codeMap.put(43, "大雪");
		codeMap.put(44, "多雲");
		codeMap.put(45, "雷陣雨");
		codeMap.put(46, "陣雪");
		codeMap.put(47, "雷陣雨");
		codeMap.put(3200, "資料錯誤");		
	}
}