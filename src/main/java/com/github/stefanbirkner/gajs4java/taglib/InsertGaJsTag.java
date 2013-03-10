package com.github.stefanbirkner.gajs4java.taglib;

import static com.github.stefanbirkner.gajs4java.core.model.AnalyticsScript.STANDARD;
import static com.github.stefanbirkner.gajs4java.core.model.Protocol.DECIDE_BY_JAVASCRIPT;
import static com.github.stefanbirkner.gajs4java.core.util.ProtocolUtil.finalProtocolForRequestAndBaseProtocol;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import com.github.stefanbirkner.gajs4java.core.model.AnalyticsScript;
import com.github.stefanbirkner.gajs4java.core.model.Protocol;
import com.github.stefanbirkner.gajs4java.core.render.LoadAnalyticsScriptRenderer;

public class InsertGaJsTag extends SimpleTagSupport {
	private static final LoadAnalyticsScriptRenderer RENDERER = new LoadAnalyticsScriptRenderer();
	private AnalyticsScript analyticsScript = STANDARD;
	private Protocol protocol = DECIDE_BY_JAVASCRIPT;

	public void setAnalyticsScript(AnalyticsScript analyticsScript) {
		this.analyticsScript = analyticsScript;
	}

	public void setProtocol(Protocol protocol) {
		this.protocol = protocol;
	}

	@Override
	public void doTag() throws IOException {
		Writer w = getJspContext().getOut();
		Protocol finalProtocol = finalProtocolForRequestAndBaseProtocol(
				request(), protocol);
		RENDERER.writeScriptLoadingCodeToWriter(w, finalProtocol,
				analyticsScript);
	}

	private ServletRequest request() {
		return ((PageContext) getJspContext()).getRequest();
	}
}
