/*
 * Copyright 2018 datagear.tech
 *
 * Licensed under the LGPLv3 license:
 * http://www.gnu.org/licenses/lgpl-3.0.html
 */

package org.datagear.analysis.support.html;

import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import org.datagear.analysis.ChartDefinition;
import org.datagear.analysis.RenderContext;
import org.datagear.analysis.TemplateDashboardWidgetResManager;
import org.datagear.analysis.support.DefaultRenderContext;
import org.datagear.analysis.support.FileTemplateDashboardWidgetResManager;
import org.datagear.analysis.support.SimpleChartWidgetSource;
import org.datagear.analysis.support.SimpleDashboardThemeSource;
import org.datagear.analysis.support.html.HtmlTplDashboardRenderAttr.DefaultHtmlTitleHandler;
import org.datagear.analysis.support.html.HtmlTplDashboardRenderAttr.WebContext;
import org.datagear.analysis.support.html.HtmlTplDashboardWidgetHtmlRenderer.ChartInfo;
import org.datagear.analysis.support.html.HtmlTplDashboardWidgetHtmlRenderer.DashboardInfo;
import org.datagear.util.IOUtil;
import org.junit.Assert;
import org.junit.Test;

/**
 * {@linkplain HtmlTplDashboardWidgetHtmlRenderer}单元测试类。
 * 
 * @author datagear@163.com
 *
 */
public class HtmlTplDashboardWidgetHtmlRendererTest
{
	private HtmlTplDashboardWidgetHtmlRenderer renderer;

	private TemplateDashboardWidgetResManager resManager;

	protected static final String TEMPLATE_NAME = "index.html";

	protected static final String IMPORT_CONTENT_JQUERY = "<script type=\"text/javascript\" src=\"jquery.js\"></script>";

	protected static final String IMPORT_CONTENT_UTIL = "<script type=\"text/javascript\" src=\"util.js\"></script>";

	protected static final String IMPORT_CONTENT_THEME = "<link rel=\"stylesheet\" type=\"text/css\" href=\"theme.css\">";

	protected static final String IMPORT_CONTENT_STYLE = "<link rel=\"stylesheet\" type=\"text/css\" href=\"style.css\">";

	public HtmlTplDashboardWidgetHtmlRendererTest() throws Exception
	{
		super();

		HtmlChartPlugin chartPlugin = HtmlChartPluginTest.createHtmlChartPlugin();

		HtmlChartWidget htmlChartWidget = new HtmlChartWidget("chart-widget-01", "chart-widget-01",
				ChartDefinition.EMPTY_CHART_DATA_SET, chartPlugin);

		this.resManager = new FileTemplateDashboardWidgetResManager(
				"src/test/resources/org/datagear/analysis/support/html/htmlTplDashboardWidgets/html");

		this.renderer = new HtmlTplDashboardWidgetHtmlRenderer(new SimpleChartWidgetSource(htmlChartWidget));
	}

	@Test
	public void renderTest() throws Exception
	{
		HtmlTplDashboardWidget dashboardWidget = createHtmlTplDashboardWidget();

		RenderContext renderContext = new DefaultRenderContext();
		StringWriter out = new StringWriter();
		buildHtmlTplDashboardRenderAttr(renderContext, out);

		HtmlTplDashboard dashboard = dashboardWidget.render(renderContext);

		getHtmlWithPrint(out);

		Assert.assertEquals(6, dashboard.getCharts().size());
	}

	@Test
	public void renderDashboardTest() throws Throwable
	{
		HtmlTplDashboardWidget dashboardWidget = createHtmlTplDashboardWidget();

		// 看板属性，双引号
		{
			String template = "<html dg-dashboard-var=\"myDashboard\" dg-dashboard-factory=\"myDashboardFactory\" "
					+ " dg-dashboard-unimport=\"jquery\"><head></head><body></body></html>";

			RenderContext renderContext = new DefaultRenderContext();
			StringWriter out = new StringWriter();
			HtmlTplDashboardRenderAttr renderAttr = buildHtmlTplDashboardRenderAttr(renderContext, out);

			HtmlTplDashboard dashboard = this.renderer.createDashboard(renderContext, dashboardWidget, template);

			DashboardInfo dashboardInfo = this.renderer.renderDashboard(renderContext, dashboard, TEMPLATE_NAME,
					IOUtil.getReader(template), renderAttr);

			String html = getHtmlWithPrint(out);

			Assert.assertEquals("myDashboard", dashboardInfo.getDashboardVar());
			Assert.assertEquals("myDashboardFactory", dashboardInfo.getDashboardFactoryVar());
			Assert.assertEquals("jquery", dashboardInfo.getImportExclude());
			Assert.assertFalse(html.contains(IMPORT_CONTENT_JQUERY));
			Assert.assertTrue(html.contains(IMPORT_CONTENT_UTIL));
			Assert.assertTrue(html.contains(IMPORT_CONTENT_THEME));
			Assert.assertTrue(html.contains(IMPORT_CONTENT_STYLE));
			Assert.assertTrue(html.contains("var DataGearDashboardTmp"));
			Assert.assertTrue(html.contains("myDashboardFactory.init(DataGearDashboardTmp);"));
			Assert.assertTrue(html.contains("window.myDashboard=DataGearDashboardTmp;"));
		}

		// 看板属性，无引号
		{
			String template = "<html dg-dashboard-var=myDashboard dg-dashboard-factory=myDashboardFactory "
					+ " dg-dashboard-unimport=jquery><head></head><body></body></html>";

			RenderContext renderContext = new DefaultRenderContext();
			StringWriter out = new StringWriter();
			HtmlTplDashboardRenderAttr renderAttr = buildHtmlTplDashboardRenderAttr(renderContext, out);

			HtmlTplDashboard dashboard = this.renderer.createDashboard(renderContext, dashboardWidget, template);

			DashboardInfo dashboardInfo = this.renderer.renderDashboard(renderContext, dashboard, TEMPLATE_NAME,
					IOUtil.getReader(template), renderAttr);

			String html = getHtmlWithPrint(out);

			Assert.assertEquals("myDashboard", dashboardInfo.getDashboardVar());
			Assert.assertEquals("myDashboardFactory", dashboardInfo.getDashboardFactoryVar());
			Assert.assertEquals("jquery", dashboardInfo.getImportExclude());
			Assert.assertFalse(html.contains(IMPORT_CONTENT_JQUERY));
			Assert.assertTrue(html.contains(IMPORT_CONTENT_UTIL));
			Assert.assertTrue(html.contains(IMPORT_CONTENT_THEME));
			Assert.assertTrue(html.contains(IMPORT_CONTENT_STYLE));
			Assert.assertTrue(html.contains("var DataGearDashboardTmp"));
			Assert.assertTrue(html.contains("myDashboardFactory.init(DataGearDashboardTmp);"));
			Assert.assertTrue(html.contains("window.myDashboard=DataGearDashboardTmp;"));
		}

		// 看板属性，单引号
		{
			String template = "<html dg-dashboard-var='myDashboard' dg-dashboard-factory='myDashboardFactory' "
					+ " dg-dashboard-unimport='jquery'><head></head><body></body></html>";

			RenderContext renderContext = new DefaultRenderContext();
			StringWriter out = new StringWriter();
			HtmlTplDashboardRenderAttr renderAttr = buildHtmlTplDashboardRenderAttr(renderContext, out);

			HtmlTplDashboard dashboard = this.renderer.createDashboard(renderContext, dashboardWidget, template);

			DashboardInfo dashboardInfo = this.renderer.renderDashboard(renderContext, dashboard, TEMPLATE_NAME,
					IOUtil.getReader(template), renderAttr);

			String html = getHtmlWithPrint(out);

			Assert.assertEquals("myDashboard", dashboardInfo.getDashboardVar());
			Assert.assertEquals("myDashboardFactory", dashboardInfo.getDashboardFactoryVar());
			Assert.assertEquals("jquery", dashboardInfo.getImportExclude());
			Assert.assertFalse(html.contains(IMPORT_CONTENT_JQUERY));
			Assert.assertTrue(html.contains(IMPORT_CONTENT_UTIL));
			Assert.assertTrue(html.contains(IMPORT_CONTENT_THEME));
			Assert.assertTrue(html.contains(IMPORT_CONTENT_STYLE));
			Assert.assertTrue(html.contains("var DataGearDashboardTmp"));
			Assert.assertTrue(html.contains("myDashboardFactory.init(DataGearDashboardTmp);"));
			Assert.assertTrue(html.contains("window.myDashboard=DataGearDashboardTmp;"));
		}

		// 看板属性，多个导入排除值
		{
			String template = "<html dg-dashboard-var='myDashboard' dg-dashboard-factory='myDashboardFactory' "
					+ " dg-dashboard-unimport='jquery,theme, style'><head></head><body></body></html>";

			RenderContext renderContext = new DefaultRenderContext();
			StringWriter out = new StringWriter();
			HtmlTplDashboardRenderAttr renderAttr = buildHtmlTplDashboardRenderAttr(renderContext, out);

			HtmlTplDashboard dashboard = this.renderer.createDashboard(renderContext, dashboardWidget, template);

			DashboardInfo dashboardInfo = this.renderer.renderDashboard(renderContext, dashboard, TEMPLATE_NAME,
					IOUtil.getReader(template), renderAttr);

			String html = getHtmlWithPrint(out);

			Assert.assertEquals("myDashboard", dashboardInfo.getDashboardVar());
			Assert.assertEquals("myDashboardFactory", dashboardInfo.getDashboardFactoryVar());
			Assert.assertEquals("jquery,theme, style", dashboardInfo.getImportExclude());
			Assert.assertFalse(html.contains(IMPORT_CONTENT_JQUERY));
			Assert.assertTrue(html.contains(IMPORT_CONTENT_UTIL));
			Assert.assertFalse(html.contains(IMPORT_CONTENT_THEME));
			Assert.assertFalse(html.contains(IMPORT_CONTENT_STYLE));
			Assert.assertTrue(html.contains("var DataGearDashboardTmp"));
			Assert.assertTrue(html.contains("myDashboardFactory.init(DataGearDashboardTmp);"));
			Assert.assertTrue(html.contains("window.myDashboard=DataGearDashboardTmp;"));
		}

		// 看板属性，默认
		{
			String template = "<html><head></head><body></body></html>";

			RenderContext renderContext = new DefaultRenderContext();
			StringWriter out = new StringWriter();
			HtmlTplDashboardRenderAttr renderAttr = buildHtmlTplDashboardRenderAttr(renderContext, out);

			HtmlTplDashboard dashboard = this.renderer.createDashboard(renderContext, dashboardWidget, template);

			DashboardInfo dashboardInfo = this.renderer.renderDashboard(renderContext, dashboard, TEMPLATE_NAME,
					IOUtil.getReader(template), renderAttr);

			String html = getHtmlWithPrint(out);

			Assert.assertNull(dashboardInfo.getDashboardVar());
			Assert.assertNull(dashboardInfo.getDashboardFactoryVar());
			Assert.assertNull(dashboardInfo.getImportExclude());
			Assert.assertTrue(html.contains(IMPORT_CONTENT_JQUERY));
			Assert.assertTrue(html.contains(IMPORT_CONTENT_UTIL));
			Assert.assertTrue(html.contains(IMPORT_CONTENT_THEME));
			Assert.assertTrue(html.contains(IMPORT_CONTENT_STYLE));
			Assert.assertTrue(html.contains("var DataGearDashboardTmp"));
			Assert.assertTrue(html.contains("dashboardFactory.init(DataGearDashboardTmp);"));
			Assert.assertTrue(html.contains("window.dashboard=DataGearDashboardTmp;"));
		}

		// 图表属性
		{
			String template = "<html><head></head><body>" + HtmlChartPlugin.HTML_NEW_LINE
					+ "<div id=\"element_1\" dg-chart-widget=\"chartwidget_1\"></div>" + HtmlChartPlugin.HTML_NEW_LINE
					+ "<div id='element_2' dg-chart-widget='chartwidget_2'></div>" + HtmlChartPlugin.HTML_NEW_LINE
					+ "<div id=element_3 dg-chart-widget=chartwidget_3></div>" + HtmlChartPlugin.HTML_NEW_LINE
					+ "<div sdf abc def 12345677788 // >"
					//
					+ HtmlChartPlugin.HTML_NEW_LINE + "<div   id=element_4    dg-chart-widget=chartwidget_4    ></div>"
					+ HtmlChartPlugin.HTML_NEW_LINE + "<div   id  =  element_5    dg-chart-widget=  chartwidget_5 />"
					+ HtmlChartPlugin.HTML_NEW_LINE + "<div   id=element_6    dg-chart-widget=chartwidget_6  />"
					+ HtmlChartPlugin.HTML_NEW_LINE + "<div   id=element_7    dg-chart-widget=chartwidget_7  /  >"
					+ HtmlChartPlugin.HTML_NEW_LINE + "<div     dg-chart-widget=chartwidget_8    /  >"
					//
					+ HtmlChartPlugin.HTML_NEW_LINE + "<div     dg-chart-widget=chartwidget_9/>"
					//
					+ HtmlChartPlugin.HTML_NEW_LINE + "<div     dg-chart-widget='' />"
					//
					+ HtmlChartPlugin.HTML_NEW_LINE + "<div     dg-chart-widget=\"\"  />"
					//
					+ HtmlChartPlugin.HTML_NEW_LINE + "</body></html>";

			RenderContext renderContext = new DefaultRenderContext();
			StringWriter out = new StringWriter();
			HtmlTplDashboardRenderAttr renderAttr = buildHtmlTplDashboardRenderAttr(renderContext, out);

			HtmlTplDashboard dashboard = this.renderer.createDashboard(renderContext, dashboardWidget, template);

			DashboardInfo dashboardInfo = this.renderer.renderDashboard(renderContext, dashboard, TEMPLATE_NAME,
					IOUtil.getReader(template), renderAttr);

			String html = getHtmlWithPrint(out);

			List<ChartInfo> chartInfos = dashboardInfo.getChartInfos();
			Assert.assertEquals(9, chartInfos.size());

			for (int i = 0; i < 7; i++)
			{
				ChartInfo chartInfo = chartInfos.get(i);
				Assert.assertEquals("element_" + (i + 1), chartInfo.getElementId());
				Assert.assertEquals("chartwidget_" + (i + 1), chartInfo.getWidgetId());
			}

			Assert.assertTrue(html.contains("<html><head>"));
			Assert.assertTrue(html.contains("</head><body>"));
			Assert.assertTrue(html.contains("<div id=\"element_1\" dg-chart-widget=\"chartwidget_1\"></div>"));
			Assert.assertTrue(html.contains("<div id='element_2' dg-chart-widget='chartwidget_2'></div>"));
			Assert.assertTrue(html.contains("<div id=element_3 dg-chart-widget=chartwidget_3></div>"));
			Assert.assertTrue(html.contains("<div sdf abc def 12345677788 // >"));
			Assert.assertTrue(html.contains("<div   id=element_4    dg-chart-widget=chartwidget_4    ></div>"));
			Assert.assertTrue(html.contains("<div   id  =  element_5    dg-chart-widget=  chartwidget_5 />"));
			Assert.assertTrue(html.contains("<div   id=element_6    dg-chart-widget=chartwidget_6  />"));
			Assert.assertTrue(html.contains("<div   id=element_7    dg-chart-widget=chartwidget_7  /  >"));
			Assert.assertTrue(
					html.contains("<div     dg-chart-widget=chartwidget_8    /   id=\"DataGearChartElement7\" >"));
			Assert.assertTrue(html.contains("<div     dg-chart-widget=chartwidget_9 id=\"DataGearChartElement8\" />"));
			Assert.assertTrue(html.contains("<div     dg-chart-widget='' />"));
			Assert.assertTrue(html.contains("<div     dg-chart-widget=\"\"  />"));
			Assert.assertTrue(html.contains("</body></html>"));
		}

		// 处理标题
		{
			String template = "<html><head><title>abc</title></head><body><title>sdf</title></body></html>";

			RenderContext renderContext = new DefaultRenderContext();
			StringWriter out = new StringWriter();
			HtmlTplDashboardRenderAttr renderAttr = buildHtmlTplDashboardRenderAttr(renderContext, out);

			DefaultHtmlTitleHandler htmlTitleHandler = new DefaultHtmlTitleHandler("-suffix");
			renderAttr.setHtmlTitleHandler(renderContext, htmlTitleHandler);

			HtmlTplDashboard dashboard = this.renderer.createDashboard(renderContext, dashboardWidget, template);

			this.renderer.renderDashboard(renderContext, dashboard, TEMPLATE_NAME, IOUtil.getReader(template),
					renderAttr);

			String html = getHtmlWithPrint(out);

			Assert.assertTrue(html.contains("<title>abc-suffix</title></head>"));
			Assert.assertTrue(html.contains("<title>sdf</title>"));
		}

		// 处理标题：没有<title></title>标签
		{
			String template = "<html><head></head><body></body></html>";

			RenderContext renderContext = new DefaultRenderContext();
			StringWriter out = new StringWriter();
			HtmlTplDashboardRenderAttr renderAttr = buildHtmlTplDashboardRenderAttr(renderContext, out);

			DefaultHtmlTitleHandler htmlTitleHandler = new DefaultHtmlTitleHandler("-suffix");
			renderAttr.setHtmlTitleHandler(renderContext, htmlTitleHandler);

			HtmlTplDashboard dashboard = this.renderer.createDashboard(renderContext, dashboardWidget, template);

			this.renderer.renderDashboard(renderContext, dashboard, TEMPLATE_NAME, IOUtil.getReader(template),
					renderAttr);

			String html = getHtmlWithPrint(out);

			Assert.assertTrue(html.contains("<title>-suffix</title></head>"));
		}

		// 处理标题：没有<title></title>标签
		{
			String template = "<html><head></head><body></body></html>";

			RenderContext renderContext = new DefaultRenderContext();
			StringWriter out = new StringWriter();
			HtmlTplDashboardRenderAttr renderAttr = buildHtmlTplDashboardRenderAttr(renderContext, out);

			DefaultHtmlTitleHandler htmlTitleHandler = new DefaultHtmlTitleHandler("-suffix", "generated");
			renderAttr.setHtmlTitleHandler(renderContext, htmlTitleHandler);

			HtmlTplDashboard dashboard = this.renderer.createDashboard(renderContext, dashboardWidget, template);

			this.renderer.renderDashboard(renderContext, dashboard, TEMPLATE_NAME, IOUtil.getReader(template),
					renderAttr);

			String html = getHtmlWithPrint(out);

			Assert.assertTrue(html.contains("<title>generated</title></head>"));
		}

		// 处理标题：<title/>
		{
			String template = "<html><head><title/></head><body></body></html>";

			RenderContext renderContext = new DefaultRenderContext();
			StringWriter out = new StringWriter();
			HtmlTplDashboardRenderAttr renderAttr = buildHtmlTplDashboardRenderAttr(renderContext, out);

			DefaultHtmlTitleHandler htmlTitleHandler = new DefaultHtmlTitleHandler("-suffix", "generated");
			renderAttr.setHtmlTitleHandler(renderContext, htmlTitleHandler);

			HtmlTplDashboard dashboard = this.renderer.createDashboard(renderContext, dashboardWidget, template);

			this.renderer.renderDashboard(renderContext, dashboard, TEMPLATE_NAME, IOUtil.getReader(template),
					renderAttr);

			String html = getHtmlWithPrint(out);

			Assert.assertTrue(html.contains("<title/><title>generated</title></head>"));
		}

		// 处理标题：没有</title>
		{
			String template = "<html><head><title></head><body></body></html>";

			RenderContext renderContext = new DefaultRenderContext();
			StringWriter out = new StringWriter();
			HtmlTplDashboardRenderAttr renderAttr = buildHtmlTplDashboardRenderAttr(renderContext, out);

			DefaultHtmlTitleHandler htmlTitleHandler = new DefaultHtmlTitleHandler("-suffix", "generated");
			renderAttr.setHtmlTitleHandler(renderContext, htmlTitleHandler);

			HtmlTplDashboard dashboard = this.renderer.createDashboard(renderContext, dashboardWidget, template);

			this.renderer.renderDashboard(renderContext, dashboard, TEMPLATE_NAME, IOUtil.getReader(template),
					renderAttr);

			String html = getHtmlWithPrint(out);

			Assert.assertTrue(html.contains("<title><title>generated</title></head>"));
		}

		// 没有<head></head>
		{
			String template = "<html><body></body></html>";

			RenderContext renderContext = new DefaultRenderContext();
			StringWriter out = new StringWriter();
			HtmlTplDashboardRenderAttr renderAttr = buildHtmlTplDashboardRenderAttr(renderContext, out);

			HtmlTplDashboard dashboard = this.renderer.createDashboard(renderContext, dashboardWidget, template);

			this.renderer.renderDashboard(renderContext, dashboard, TEMPLATE_NAME, IOUtil.getReader(template),
					renderAttr);

			String html = getHtmlWithPrint(out);

			Assert.assertTrue(html.contains("<html><body>" + this.renderer.getNewLine() + "<style"));
			Assert.assertTrue(html.contains("</script>" + this.renderer.getNewLine() + "</body></html>"));
			Assert.assertEquals(1, countOf(html, "<link rel=\"stylesheet\" type=\"text/css\" href=\"style.css\">"));
			Assert.assertEquals(1, countOf(html, "DataGearDashboardTmp.render();"));
		}

		// 没有<body></body>
		{
			String template = "<html><head></head></html>";

			RenderContext renderContext = new DefaultRenderContext();
			StringWriter out = new StringWriter();
			HtmlTplDashboardRenderAttr renderAttr = buildHtmlTplDashboardRenderAttr(renderContext, out);

			HtmlTplDashboard dashboard = this.renderer.createDashboard(renderContext, dashboardWidget, template);

			this.renderer.renderDashboard(renderContext, dashboard, TEMPLATE_NAME, IOUtil.getReader(template),
					renderAttr);

			String html = getHtmlWithPrint(out);

			Assert.assertTrue(html.contains("<html><head>" + this.renderer.getNewLine() + "<style"));
			Assert.assertTrue(html.contains(
					"<link rel=\"stylesheet\" type=\"text/css\" href=\"style.css\"></head></html><script type=\"text/javascript\">"));
			Assert.assertEquals(1, countOf(html, "<link rel=\"stylesheet\" type=\"text/css\" href=\"style.css\">"));
			Assert.assertEquals(1, countOf(html, "DataGearDashboardTmp.render();"));
			Assert.assertTrue(html.endsWith("</script>" + renderer.getNewLine()));
		}

		// <html></html>
		{
			String template = "<html></html>";

			RenderContext renderContext = new DefaultRenderContext();
			StringWriter out = new StringWriter();
			HtmlTplDashboardRenderAttr renderAttr = buildHtmlTplDashboardRenderAttr(renderContext, out);

			HtmlTplDashboard dashboard = this.renderer.createDashboard(renderContext, dashboardWidget, template);

			this.renderer.renderDashboard(renderContext, dashboard, TEMPLATE_NAME, IOUtil.getReader(template),
					renderAttr);

			String html = getHtmlWithPrint(out);

			Assert.assertTrue(html.contains("</html>" + this.renderer.getNewLine() + "<style"));
			Assert.assertTrue(html.contains(
					"<link rel=\"stylesheet\" type=\"text/css\" href=\"style.css\"><script type=\"text/javascript\">"));
			Assert.assertEquals(1, countOf(html, "<link rel=\"stylesheet\" type=\"text/css\" href=\"style.css\">"));
			Assert.assertEquals(1, countOf(html, "DataGearDashboardTmp.render();"));
			Assert.assertTrue(html.endsWith("</script>" + renderer.getNewLine()));
		}
	}
	
	@Test
	public void renderDashboardTestForLoadableChartWidgets() throws Throwable
	{
		HtmlTplDashboardWidget dashboardWidget = createHtmlTplDashboardWidget();

		{
			String template = "<html><head></head><body></body></html>";

			RenderContext renderContext = new DefaultRenderContext();
			StringWriter out = new StringWriter();
			HtmlTplDashboardRenderAttr renderAttr = buildHtmlTplDashboardRenderAttr(renderContext, out);

			HtmlTplDashboard dashboard = this.renderer.createDashboard(renderContext, dashboardWidget, template);

			DashboardInfo dashboardInfo = this.renderer.renderDashboard(renderContext, dashboard, TEMPLATE_NAME,
					IOUtil.getReader(template), renderAttr);

			Assert.assertNull(dashboardInfo.getLoadableChartWidgets());
			Assert.assertNull(dashboard.getLoadableChartWidgetsPattern());
		}
		{
			String template = "<html dg-loadable-chart-widgets=\"all\"><head></head><body></body></html>";

			RenderContext renderContext = new DefaultRenderContext();
			StringWriter out = new StringWriter();
			HtmlTplDashboardRenderAttr renderAttr = buildHtmlTplDashboardRenderAttr(renderContext, out);

			HtmlTplDashboard dashboard = this.renderer.createDashboard(renderContext, dashboardWidget, template);

			DashboardInfo dashboardInfo = this.renderer.renderDashboard(renderContext, dashboard, TEMPLATE_NAME,
					IOUtil.getReader(template), renderAttr);

			Assert.assertEquals("all", dashboardInfo.getLoadableChartWidgets());
			Assert.assertTrue(dashboard.getLoadableChartWidgetsPattern().isPatternAll());
		}
		{
			String template = "<html dg-loadable-chart-widgets='none'><head></head><body></body></html>";

			RenderContext renderContext = new DefaultRenderContext();
			StringWriter out = new StringWriter();
			HtmlTplDashboardRenderAttr renderAttr = buildHtmlTplDashboardRenderAttr(renderContext, out);

			HtmlTplDashboard dashboard = this.renderer.createDashboard(renderContext, dashboardWidget, template);

			DashboardInfo dashboardInfo = this.renderer.renderDashboard(renderContext, dashboard, TEMPLATE_NAME,
					IOUtil.getReader(template), renderAttr);

			Assert.assertEquals("none", dashboardInfo.getLoadableChartWidgets());
			Assert.assertTrue(dashboard.getLoadableChartWidgetsPattern().isPatternNone());
		}
		{
			String template = "<html dg-loadable-chart-widgets='permitted'><head></head><body></body></html>";

			RenderContext renderContext = new DefaultRenderContext();
			StringWriter out = new StringWriter();
			HtmlTplDashboardRenderAttr renderAttr = buildHtmlTplDashboardRenderAttr(renderContext, out);

			HtmlTplDashboard dashboard = this.renderer.createDashboard(renderContext, dashboardWidget, template);

			DashboardInfo dashboardInfo = this.renderer.renderDashboard(renderContext, dashboard, TEMPLATE_NAME,
					IOUtil.getReader(template), renderAttr);

			Assert.assertEquals("permitted", dashboardInfo.getLoadableChartWidgets());
			Assert.assertTrue(dashboard.getLoadableChartWidgetsPattern().isPatternPermitted());
		}
		{
			String template = "<html dg-loadable-chart-widgets='a-widget-id'><head></head><body></body></html>";

			RenderContext renderContext = new DefaultRenderContext();
			StringWriter out = new StringWriter();
			HtmlTplDashboardRenderAttr renderAttr = buildHtmlTplDashboardRenderAttr(renderContext, out);

			HtmlTplDashboard dashboard = this.renderer.createDashboard(renderContext, dashboardWidget, template);

			DashboardInfo dashboardInfo = this.renderer.renderDashboard(renderContext, dashboard, TEMPLATE_NAME,
					IOUtil.getReader(template), renderAttr);

			Assert.assertEquals("a-widget-id", dashboardInfo.getLoadableChartWidgets());
			Assert.assertTrue(dashboard.getLoadableChartWidgetsPattern().isPatternList());
			Assert.assertTrue(dashboard.getLoadableChartWidgetsPattern().getChartWidgetIds().size() == 1);
			Assert.assertTrue(dashboard.getLoadableChartWidgetsPattern().inList("a-widget-id"));
		}
		{
			String template = "<html dg-loadable-chart-widgets='widget-id-0,widget-id-1'><head></head><body></body></html>";

			RenderContext renderContext = new DefaultRenderContext();
			StringWriter out = new StringWriter();
			HtmlTplDashboardRenderAttr renderAttr = buildHtmlTplDashboardRenderAttr(renderContext, out);

			HtmlTplDashboard dashboard = this.renderer.createDashboard(renderContext, dashboardWidget, template);

			DashboardInfo dashboardInfo = this.renderer.renderDashboard(renderContext, dashboard, TEMPLATE_NAME,
					IOUtil.getReader(template), renderAttr);

			Assert.assertEquals("widget-id-0,widget-id-1", dashboardInfo.getLoadableChartWidgets());
			Assert.assertTrue(dashboard.getLoadableChartWidgetsPattern().isPatternList());
			Assert.assertTrue(dashboard.getLoadableChartWidgetsPattern().getChartWidgetIds().size() == 2);
			Assert.assertTrue(dashboard.getLoadableChartWidgetsPattern().inList("widget-id-0"));
			Assert.assertTrue(dashboard.getLoadableChartWidgetsPattern().inList("widget-id-1"));
		}
		{
			String template = "<html dg-loadable-chart-widgets='widget-id-0, widget-id-1 , widget-id-2 '><head></head><body></body></html>";

			RenderContext renderContext = new DefaultRenderContext();
			StringWriter out = new StringWriter();
			HtmlTplDashboardRenderAttr renderAttr = buildHtmlTplDashboardRenderAttr(renderContext, out);

			HtmlTplDashboard dashboard = this.renderer.createDashboard(renderContext, dashboardWidget, template);

			DashboardInfo dashboardInfo = this.renderer.renderDashboard(renderContext, dashboard, TEMPLATE_NAME,
					IOUtil.getReader(template), renderAttr);

			Assert.assertEquals("widget-id-0, widget-id-1 , widget-id-2", dashboardInfo.getLoadableChartWidgets());
			Assert.assertTrue(dashboard.getLoadableChartWidgetsPattern().isPatternList());
			Assert.assertTrue(dashboard.getLoadableChartWidgetsPattern().getChartWidgetIds().size() == 3);
			Assert.assertTrue(dashboard.getLoadableChartWidgetsPattern().inList("widget-id-0"));
			Assert.assertTrue(dashboard.getLoadableChartWidgetsPattern().inList("widget-id-1"));
			Assert.assertTrue(dashboard.getLoadableChartWidgetsPattern().inList("widget-id-2"));
		}
	}

	protected HtmlTplDashboardRenderAttr buildHtmlTplDashboardRenderAttr(RenderContext renderContext, Writer out)
	{
		HtmlTplDashboardRenderAttr renderAttr = new HtmlTplDashboardRenderAttr();
		renderAttr.inflate(renderContext, out);
		renderAttr.setWebContext(renderContext, new WebContext(""));
		renderAttr.setDashboardTheme(renderContext, SimpleDashboardThemeSource.THEME_LIGHT);
		renderAttr.setImportList(renderContext, buildImportList());

		return renderAttr;
	}

	protected List<HtmlTplDashboardImport> buildImportList()
	{
		List<HtmlTplDashboardImport> list = new ArrayList<>();

		list.add(HtmlTplDashboardImport.valueOf("jquery", IMPORT_CONTENT_JQUERY));
		list.add(HtmlTplDashboardImport.valueOf("util", IMPORT_CONTENT_UTIL));
		list.add(HtmlTplDashboardImport.valueOf("theme", IMPORT_CONTENT_THEME));
		list.add(HtmlTplDashboardImport.valueOf("style", IMPORT_CONTENT_STYLE));

		return list;
	}

	protected HtmlTplDashboardWidget createHtmlTplDashboardWidget()
	{
		HtmlTplDashboardWidget dashboardWidget = new HtmlTplDashboardWidget("widget01", TEMPLATE_NAME, this.renderer,
				this.resManager);
		return dashboardWidget;
	}

	protected int countOf(String src, String sub)
	{
		int c = 0;

		int idx = -1;
		while (true)
		{
			idx = src.indexOf(sub, (idx < 0 ? 0 : idx + sub.length()));

			if (idx < 0)
				break;
			else
				c++;
		}

		return c;
	}

	protected String getHtmlWithPrint(StringWriter out)
	{
		String html = out.toString();

		System.out.println(html);
		System.out.println("");
		System.out.println("-----------------------");
		System.out.println("");

		return html;
	}
}
