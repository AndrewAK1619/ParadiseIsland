package pl.example.components.offer.location.country.image;

import java.util.Random;

import org.springframework.stereotype.Service;

@Service
public class CountryImageService {
	
	/* Metods only for put example data / Example Img */
	public String getPathToRandomExampleImg() {
		Random r = new Random();
		int a = r.nextInt((27 - 1) + 1) + 1;
		System.out.println(a);
		return "src/main/resources/static/img/default/ExampleImg/example" + a + ".jpg";
	}
}
