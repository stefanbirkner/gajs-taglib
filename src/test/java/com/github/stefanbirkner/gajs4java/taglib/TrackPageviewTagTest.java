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

public class TrackPageviewTagTest {
	@ClassRule
	public static final Jetty SERVER = new Jetty();

	@Test
	public void rendersTagWithAccountAttributeOnly() throws Exception {
		String url = "http://127.0.0.1:8080/trackPageview/minimal.jsp";
		assertEquals(snippetWithName("minimal"), pageSourceForUrl(url));
	}

	@Test
	public void rendersTagWithAllAttributes() throws Exception {
		String url = "http://127.0.0.1:8080/trackPageview/complete.jsp";
		assertEquals(snippetWithName("complete"), pageSourceForUrl(url));
	}

	private String pageSourceForUrl(String urlAsString)
			throws MalformedURLException, IOException {
		URL url = new URL(urlAsString);
		return IOUtils.toString(url.openStream());
	}

	private String snippetWithName(String name) throws IOException {
		InputStream is = getClass().getResourceAsStream(
				"trackPageview." + name + ".snippet");
		return IOUtils.toString(is);
	}
}
