package com.unionpay.acp.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.unionpay.acp.Test_FrontConsume;

/**
 * Servlet implementation class PayServlet
 */
public class PayServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private Test_FrontConsume test;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PayServlet() {
        super();
        test = new Test_FrontConsume();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("1");
		String html=test.forwardPay();
		PrintWriter out=response.getWriter();
		out.print(html);
		out.flush();
		out.close();
				
		String resp=request.getParameterMap().toString();
		System.out.println(resp);
	}
}
