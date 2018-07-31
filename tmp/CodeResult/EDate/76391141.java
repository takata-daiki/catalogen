package ee.widespace.banner.servlet;

import ee.widespace.banner.statistic.*;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.xml.parsers.*;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;

import ee.widespace.banner.data.*;
import ee.widespace.banner.*;


/**
 * Insert the type's description here.
 * Creation date: (6/13/2001 11:39:20 AM)
 * @author: Jan Abel
 */
public class BannerService extends HttpServlet {
/**
 * doGet method comment.
 */
public void doGet( HttpServletRequest req,
					HttpServletResponse res )
throws ServletException, IOException {
	doService( req, res );
}
/**
 * doPost method comment.
 */
public void doPost( HttpServletRequest req,
					HttpServletResponse res )
throws ServletException, IOException {
	doService( req, res );
}
/**
 * doGet method comment.
 */
public void doService(HttpServletRequest req, HttpServletResponse res)
	throws ServletException, IOException {
	String    act     = req.getParameter("action");

	String	  atrSite = req.getParameter("site");
	String	  atrRt   = req.getParameter("rotation");	
	
	if (act == null)
		act = "";
	else
		if (act.equals("new site")) {
			try {
				Site site     = new Site();
				site.name     = req.getParameter("name");
				site.url      = req.getParameter("url");
				site.email    = req.getParameter("email");
				site.password = req.getParameter("password");
				site.info     = req.getParameter("info");

				if ( (site.name == null) || ( site.name.length() == 0 ) ){
					req.setAttribute("e" , "Field 'Name' is empty");					
					req.getRequestDispatcher( "/admin/addSite.jsp" )
						.forward( req, res );
					return;
				}

				Connection conn = getConnection();
				try {
					BannerDataImpl data = new BannerDataImpl(conn);
					data.addSite(site);
					conn.commit();
				} finally {
					conn.close();
				}

				atrSite = req.getParameter("name");
				res.sendRedirect("site.jsp?site=" + atrSite);
			} catch (Exception e) {
									
					req.setAttribute( "returnPage" , "allSitesMenu.jsp" );
					req.setAttribute( "e" , e.getMessage() );
					req.getRequestDispatcher("/admin/errorPage.jsp").forward(req, res);
				return;
			}
		} else
		if (act.equals("new banner")) {
		try {
		Banner banner   = new Banner();
		banner.name     = req.getParameter("name");
		banner.url      = req.getParameter("url");
		banner.img      = req.getParameter("img");
		banner.site     = req.getParameter("site");
		banner.alt      = req.getParameter("alt");
		banner.info     = req.getParameter("info");
		
		if ( (banner.name == null) || ( banner.name.length() == 0 ) ){
			req.setAttribute("e" , "Field 'Name' is empty");					
			req.getRequestDispatcher( "/admin/addBanner.jsp" )
				.forward( req, res );
				
				return;
			}
					
		Connection conn = getConnection();
		try {
			BannerDataImpl data = new BannerDataImpl(conn);
			data.addBanner(banner);
			conn.commit();
		} finally {
			conn.close();
		}
		res.sendRedirect("site.jsp?site=" + atrSite);
		
		} catch (Exception e) {
			req.setAttribute( "returnPage" , "site.jsp?site=" + atrSite  );
			req.setAttribute( "e" , e.getMessage() );
			req.getRequestDispatcher("/admin/errorPage.jsp").forward(req, res);
		return;
		}
		} else
		if (act.equals("new rt")) {
		try {
			BannerRt bannerRt = new BannerRt();
			bannerRt.name = req.getParameter("name");
			bannerRt.info = req.getParameter("info");
			
		if ( (bannerRt.name == null) || ( bannerRt.name.length() == 0 ) ){
			req.setAttribute("e" , "Field 'Name' is empty");					
			req.getRequestDispatcher( "/admin/addRotation.jsp" )
				.forward( req, res );
				return;
		}
			Connection conn = getConnection();
			try {
				BannerDataImpl data = new BannerDataImpl(conn);
				data.addBannerRt(bannerRt);
				conn.commit();
			} finally {
				conn.close();
			}
			res.sendRedirect("allRotationMenu.jsp");
		} catch (Exception e) {
			
			req.setAttribute( "returnPage" ,  "allRotationMenu.jsp" );
			req.setAttribute( "e" , e.getMessage() );
			req.getRequestDispatcher("/admin/errorPage.jsp").forward(req, res);
			return;
		}
		} else
		if (act.equals("new bannerInRt")) {
			String site = req.getParameter("site");
			String banner = req.getParameter("banner");
			if (banner == null
				|| banner.length() == 0) {
				
				req.getRequestDispatcher("/main.jsp").forward(req, res);
				return;
			}

				try {
					BannerInRt bannerInRt = new BannerInRt();
					
					bannerInRt.rotation = req.getParameter("rotation");
					bannerInRt.site = site;
					bannerInRt.banner = banner;
					bannerInRt.sdate = req.getParameter("sdate");
					bannerInRt.edate = req.getParameter("edate");
					bannerInRt.counter = Integer.parseInt(req.getParameter("counter"));
					bannerInRt.frequency = Integer.parseInt(req.getParameter("frequency"));
					
			if ( (bannerInRt.banner == null) || ( bannerInRt.banner.length() == 0 ) ){
				req.setAttribute("e" , "Field 'Name' is empty");					
				req.getRequestDispatcher( "/admin/addSite.jsp" )
					.forward( req, res );
				return;
				
			}
					Connection conn = getConnection();
					try {
						BannerDataImpl data = new BannerDataImpl(conn);
						data.addBannerInRt(bannerInRt);
						conn.commit();
					} finally {
						conn.close();
						
					}
					res.sendRedirect("rotation.jsp?rotation=" + atrRt);
				} catch (Exception e) {
					req.setAttribute( "returnPage" , "rotation.jsp?rotation=" + atrRt  );
					req.setAttribute( "e" , e.getMessage() );
					req.getRequestDispatcher("/admin/errorPage.jsp").forward(req, res);
					return;
				}
			} else
				if (act.equals("remove bannerInRt")) {
					String site = req.getParameter("site");
					String banner = req.getParameter("banner");
					if (banner == null || banner.length() == 0) {
						req.getRequestDispatcher("/admin/main.jsp").forward(req, res);					
						return;
					}

					try {
						java.sql.Connection conn = getConnection();
						try {
							BannerDataImpl data = new BannerDataImpl(conn);
							data.removeBannerInRt(req.getParameter("rotation"), site, banner);
							conn.commit();
						} finally {
							conn.close();
							
						}
						res.sendRedirect("rotation.jsp?rotation=" + atrRt);
					} catch (Exception e) {
						
						req.setAttribute( "returnPage" , "rotation.jsp?rotation=" + atrRt  );
						req.setAttribute( "e" , e.getMessage() );
						req.getRequestDispatcher("/admin/errorPage.jsp").forward(req, res);
						return;
					}
				} else
					if (act.equals("remove rt")) {
						try {
							String rotation = req.getParameter("rotation");

							Connection conn = getConnection();
							try {
								BannerDataImpl data = new BannerDataImpl(conn);
								data.removeBannerRt(rotation);
								conn.commit();
							} finally {
								conn.close();
								
							}
							res.sendRedirect("allRotationMenu.jsp");
						} catch (Exception e) {
							req.setAttribute( "returnPage" , "rotation.jsp?rotation=" + atrRt );
							req.setAttribute( "e" , e.getMessage() );
							req.getRequestDispatcher("/admin/errorPage.jsp").forward(req, res);
							return;
						}
		} else
			if (act.equals("remove site")) {
				try {
					String site = req.getParameter("site");

					Connection conn = getConnection();
					try {
						BannerDataImpl data = new BannerDataImpl(conn);
						data.removeSite(site);
						conn.commit();
					} finally {
						conn.close();
					}
					res.sendRedirect("allSitesMenu.jsp");
				} catch (Exception e) {
					req.setAttribute( "returnPage" ,  "site.jsp?site=" + atrSite );
					req.setAttribute( "e" , e.getMessage() );
					req.getRequestDispatcher("/admin/errorPage.jsp").forward(req, res);
					
					return;
				}
		} else
			if (act.equals("update rt")) {
					try {
		BannerRt rotation = new BannerRt();
				
	
				rotation.name = req.getParameter("rotation");
				rotation.info = req.getParameter("info");
				Connection conn = getConnection();
				try {
					BannerDataImpl data = new BannerDataImpl(conn);
					data.updateRt(rotation);
					conn.commit();
				} finally {
					conn.close();
					}
					res.sendRedirect("rotation.jsp?rotation=" + atrRt);
			} catch (Exception e) {
				
					req.setAttribute( "returnPage" , "allRotationMenu.jsp" );
					req.setAttribute( "e" , e.getMessage() );
					req.getRequestDispatcher("/admin/errorPage.jsp").forward(req, res);
				return;
			}
				
		}else
			if (act.equals("update site")) {
					try {
	Site    site        = new Site();
	String  oldSiteName = new String(req.getParameter("oldname"));
	
				site.name = req.getParameter("oldname");
				site.url = req.getParameter("url");
				site.email = req.getParameter("email");
				site.password = req.getParameter("password");
				site.info = req.getParameter("info");
				
				Connection conn = getConnection();
				try {
					BannerDataImpl data = new BannerDataImpl(conn);
					data.updateSite(site,oldSiteName);
					conn.commit();
				} finally {
					conn.close();
					}
					res.sendRedirect("site.jsp?site=" + atrSite);
			} catch (Exception e) {
					req.setAttribute( "returnPage" , "site.jsp?site=" +  atrSite );
					req.setAttribute( "e" , e.getMessage() );
					req.getRequestDispatcher("/admin/errorPage.jsp").forward(req, res);
				return;
			}
				
		}else
			if (act.equals("update banner")) {
			try {
				
	Banner banner = new Banner();
	String  bannerName = new String(req.getParameter("banner"));
	String  siteName   = new String(req.getParameter("site"));
	String  siteInfo   = new String(req.getParameter("info"));
	
				banner.site = siteName;
				banner.name = bannerName; 
				banner.img  = req.getParameter("img");
				banner.url  = req.getParameter("url");
				banner.alt  = req.getParameter("alt");
				banner.info = req.getParameter("bannerInfo");
				
				
				Connection conn = getConnection();
				try {
					BannerDataImpl data = new BannerDataImpl(conn);
					data.updateBanner(banner);
					conn.commit();
				} finally {conn.close();
					
					}
					res.sendRedirect("site.jsp?site=" + atrSite);
			} catch (Exception e) {
				req.setAttribute( "returnPage" , "site.jsp?site=" + atrSite );
				req.setAttribute( "e" , e.getMessage() );
				req.getRequestDispatcher("/admin/errorPage.jsp").forward(req, res);
				return;
			}
		}else
			if (act.equals("update bannerInRt")) {
			try {
				
	BannerInRt bannerInRt = new BannerInRt();
				
	bannerInRt.rotation  = req.getParameter("rotation");

	bannerInRt.site 	 = req.getParameter("site");
	bannerInRt.banner 	 = req.getParameter("banner"); 
	bannerInRt.edate	 = req.getParameter("sdate");
	bannerInRt.sdate 	 = req.getParameter("edate");
	
	bannerInRt.counter   = Integer.parseInt(req.getParameter("counter"));
	bannerInRt.frequency = Integer.parseInt(req.getParameter("frequency"));
				
				Connection conn = getConnection();
				try {
					BannerDataImpl data = new BannerDataImpl(conn);
					data.updateBannerInRt(bannerInRt);

					conn.commit();
				} finally {conn.close();
					
					}
					res.sendRedirect("rotation.jsp?rotation=" + atrRt);
			} catch (Exception e) {
				
				req.setAttribute( "returnPage" , "rotation.jsp?rotation=" + atrRt );
				req.setAttribute( "e" , e.getMessage() );
				req.getRequestDispatcher("/admin/errorPage.jsp").forward(req, res);
				return;
			}
		}else
			if (act.equals("remove banner")) {
			String site = req.getParameter("site");
			String banner = req.getParameter("banner");
						
						if (banner == null
							|| banner.length() == 0) {
							req.getRequestDispatcher("/admin/main.jsp").forward(req, res);
							return;}
	
						try {
							java.sql.Connection conn = getConnection();
							try {
								BannerDataImpl data = new BannerDataImpl(conn);
								data.removeBanner(site, banner);
								conn.commit();
		
							} finally {conn.close();
								
								}
								res.sendRedirect("site.jsp?site=" + atrSite);
						} catch (Exception e) {
							
							req.setAttribute( "returnPage" , "site.jsp?site=" + atrSite );
							req.setAttribute( "e" , e.getMessage() );
							req.getRequestDispatcher("/admin/errorPage.jsp").forward(req, res);
							return;
						}
					}
}
/**
 * getConnection
 */
public Connection getConnection() throws SQLException {
	String url = getServletContext()
		.getInitParameter( "jdbc.url" );
	
	Connection con = DriverManager.getConnection( url );
	try {
		con.setAutoCommit( false );
		return con;
	} catch ( SQLException e ) {
		con.close();
		throw e;
	}
}
}
