package com.github.stefanbirkner.gajs4java.taglib;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.io.IOUtils;
import org.junit.ClassRule;
import org.junit.Test;

import com.github.stefanbirkner.serverrule.Jetty;

public class InsertGaJsTagTest {
	@ClassRule
	public static final Jetty SERVER = new Jetty();

	@Test
	public void rendersHttpSnippet() throws Exception {
		String url = "http://127.0.0.1:8080/insertGaJs/renderer.jsp";
		assertEquals(snippetWithName("http"), pageSourceForUrl(url));
	}

	@Test
	public void rendersJavaScriptSnippet() throws Exception {
		String url = "http://127.0.0.1:8080/insertGaJs/javascript.jsp";
		assertEquals(snippetWithName("javascript"), pageSourceForUrl(url));
	}

	private String pageSourceForUrl(String urlAsString)
			throws MalformedURLException, IOException {
		URL url = new URL(urlAsString);
		return IOUtils.toString(url.openStream());
	}

	private String snippetWithName(String name) throws IOException {
		InputStream is = getClass().getResourceAsStream(
				"ga.js." + name + ".snippet");
		return IOUtils.toString(is);
	}
}
