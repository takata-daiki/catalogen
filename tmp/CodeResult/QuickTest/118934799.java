/*
 *  Copyright (C) 2011 Tasnai Amphol (tas at taska.com.au)
 * 
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 * 
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 * 
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package taska.commons.blackboard.test.misc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.AsyncContext;
import javax.servlet.DispatcherType;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Tasnai Amphol (tas at taska.com.au)
 */
public class MiscTest {

    public MiscTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void TestGetVendorIdAndHandleFromRequest()
    {
        System.out.println("Test get vendorId and handle from request object.");
        DummyHttpServletRequest request = new DummyHttpServletRequest();
        String uri = request.getRequestURI();
        Pattern pattern = Pattern.compile("/webapps/([^-]*)-([^-]*)-.*");
        Matcher matcher = pattern.matcher(uri);
        boolean matches = matcher.matches();
        assertTrue(matches);
        if(matches) {
            System.out.println("\tgroup(): " + matcher.group());
            System.out.println("\tgroupCount(): " + String.valueOf(matcher.groupCount()));
            for(int i = 0; i < matcher.groupCount() + 1; i++) {
                System.out.println("\tgroup(" + String.valueOf(i) + "): " + matcher.group(i));
            }
        }
    }

    static final class DummyHttpServletRequest implements HttpServletRequest {

        public DummyHttpServletRequest() {
            
        }

        public String getAuthType() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public Cookie[] getCookies() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public long getDateHeader(String name) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public String getHeader(String name) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public Enumeration<String> getHeaders(String name) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public Enumeration<String> getHeaderNames() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public int getIntHeader(String name) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public String getMethod() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public String getPathInfo() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public String getPathTranslated() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public String getContextPath() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public String getQueryString() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public String getRemoteUser() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public boolean isUserInRole(String role) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public Principal getUserPrincipal() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public String getRequestedSessionId() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public String getRequestURI() {
            return "/webapps/tk-qiktest-BBLEARN/quicktest/WithSession.jsp";
        }

        public StringBuffer getRequestURL() {
            return new StringBuffer("http://blackboard/webapps/tk-qiktest-BBLEARN/quicktest/WithSession.jsp");
        }

        public String getServletPath() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public HttpSession getSession(boolean create) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public HttpSession getSession() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public boolean isRequestedSessionIdValid() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public boolean isRequestedSessionIdFromCookie() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public boolean isRequestedSessionIdFromURL() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Deprecated
        public boolean isRequestedSessionIdFromUrl() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public boolean authenticate(HttpServletResponse response) throws IOException, ServletException {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public void login(String username, String password) throws ServletException {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public void logout() throws ServletException {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public Collection<Part> getParts() throws IOException, ServletException {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public Part getPart(String name) throws IOException, ServletException {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public Object getAttribute(String name) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public Enumeration<String> getAttributeNames() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public String getCharacterEncoding() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public void setCharacterEncoding(String env) throws UnsupportedEncodingException {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public int getContentLength() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public String getContentType() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public ServletInputStream getInputStream() throws IOException {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public String getParameter(String name) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public Enumeration<String> getParameterNames() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public String[] getParameterValues(String name) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public Map<String, String[]> getParameterMap() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public String getProtocol() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public String getScheme() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public String getServerName() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public int getServerPort() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public BufferedReader getReader() throws IOException {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public String getRemoteAddr() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public String getRemoteHost() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public void setAttribute(String name, Object o) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public void removeAttribute(String name) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public Locale getLocale() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public Enumeration<Locale> getLocales() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public boolean isSecure() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public RequestDispatcher getRequestDispatcher(String path) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Deprecated
        public String getRealPath(String path) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public int getRemotePort() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public String getLocalName() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public String getLocalAddr() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public int getLocalPort() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public ServletContext getServletContext() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public AsyncContext startAsync() throws IllegalStateException {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public AsyncContext startAsync(ServletRequest servletRequest, ServletResponse servletResponse) throws IllegalStateException {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public boolean isAsyncStarted() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public boolean isAsyncSupported() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public AsyncContext getAsyncContext() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public DispatcherType getDispatcherType() {
            throw new UnsupportedOperationException("Not supported yet.");
        }
        
    }
}