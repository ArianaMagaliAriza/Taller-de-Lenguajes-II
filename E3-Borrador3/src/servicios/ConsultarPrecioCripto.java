package servicios;
import java.io.IOException;
import java.net.URI;
import java.net.http.*;
import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

import org.json.JSONObject; // Necesita agregar la librería org.json para trabajar con JSON


public class ConsultarPrecioCripto extends TimerTask{
	private static final String URL_API ="https://api.coingecko.com/api/v3/simple/price?ids=bitcoin,ethereum,usd-coin,tether,dogecoin&vs_currencies=usd";
	private List<Double> precios;
	private ActualizacionCotizaciones actualizacion;
	
	public ConsultarPrecioCripto(ActualizacionCotizaciones actualizacion) {
		this.precios=new ArrayList<>();
		this.actualizacion=actualizacion;
	}
	public void run() {
		HttpClient cliente = HttpClient.newHttpClient();
		HttpRequest solicitud = HttpRequest.newBuilder()
		.uri(URI.create(URL_API))
		.GET()
		.build();
		try {
		HttpResponse<String> respuesta = cliente.send(solicitud, HttpResponse.BodyHandlers.ofString());
		if (respuesta.statusCode() == 200) {
			parsearYRetornarPrecios(respuesta.body());
			actualizacion.actualizarPrecios(this.getPrecios());
			}
		else {
				System.out.println("Error: " + respuesta.statusCode());
			}
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private void parsearYRetornarPrecios(String cuerpoRespuesta) {
		JSONObject json = new JSONObject(cuerpoRespuesta);
		List<Double> precios=new ArrayList<Double>();
		double precioBTC = json.getJSONObject("bitcoin").getDouble("usd");
		precios.add(precioBTC);
		double precioETH = json.getJSONObject("ethereum").getDouble("usd");
		precios.add(precioETH);
		double precioUSDC = json.getJSONObject("usd-coin").getDouble("usd");
		precios.add(precioUSDC);
		double precioUSDT = json.getJSONObject("tether").getDouble("usd");
		precios.add(precioUSDT);
		double precioDOGE = json.getJSONObject("dogecoin").getDouble("usd");
		precios.add(precioDOGE);
		this.setPrecios(precios);
	}

	public List<Double> getPrecios() {
		return precios;
	}

	public void setPrecios(List<Double> precios) {
		this.precios = precios;
	}
	
}