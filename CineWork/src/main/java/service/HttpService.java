package service;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HttpService {
	
	private static final String URL_BASE_CNPJ = "https://api.cnpja.com.br/companies/";
	private static final String PARAMETROS_CNPJ = "?company_max_age=365";
	private static final String API_KEY_CNPJ = "API_KEY_HERE";
	private static final String URL_BASE_CEP = "http://viacep.com.br/ws/";

	public static String getCNPJ(String cnpj) {
		OkHttpClient client = new OkHttpClient();
		String url = URL_BASE_CNPJ + cnpj + PARAMETROS_CNPJ;
		Request request = new Request.Builder().url(url).addHeader("Authorization", API_KEY_CNPJ).build();
		try {
			Response response = client.newCall(request).execute();
			return response.body().string();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static String getCEP(String cep) {
		OkHttpClient client = new OkHttpClient();
		String url = URL_BASE_CEP + cep + "/json/";
		Request request = new Request.Builder().url(url).build();
		try {
			Response response = client.newCall(request).execute();
			return response.body().string();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
