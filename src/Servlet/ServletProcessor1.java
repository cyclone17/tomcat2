package Servlet;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandler;
import javax.servlet.Servlet;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class ServletProcessor1
{
	public void process(Request request, Response response)
	{
		String uri = request.getUri();
		String servletName =new XMLUtil().getServletName(uri.substring(uri.lastIndexOf("/"))) ;
		URLClassLoader loader = null;
		try
		{
			// create a URLClassLoader
			URL[] urls = new URL[1];
			URLStreamHandler streamHandler = null;
			File classPath = new File(Response.WEB_ROOT);
			// the forming of repository is taken from the
			// createClassLoader method in
			// org.apache.catalina.startup.ClassLoaderFactory
			String repository = (new URL("file", null, classPath.getCanonicalPath() + File.separator)).toString();
			// the code for forming the URL is taken from
			// the addRepository method in
			// org.apache.catalina.loader.StandardClassLoader.
			urls[0] = new URL(null, repository, streamHandler);
			loader = new URLClassLoader(urls);
		}
		catch (IOException e)
		{
			System.out.println(e.toString());
		}
		Class myClass = null;
		try
		{
			System.out.println(servletName);
			myClass = loader.loadClass(servletName);
		}
		catch (ClassNotFoundException e)
		{
			System.out.println(e.toString());
		}
		Servlet servlet = null;
		try
		{
			//跑一下反射，初始化一个实例，然后调用这个servlet的service方法。
			servlet = (Servlet) myClass.newInstance();
			servlet.service((ServletRequest) request, (ServletResponse) response);
		}
		catch (Exception e)
		{
			System.out.println(e.toString());
		}
		catch (Throwable e)
		{
			System.out.println(e.toString());
		}
	}
}