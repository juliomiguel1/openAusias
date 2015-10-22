/*
 * Copyright (c) 2015 by Rafael Angel Aznar Aparici (rafaaznar at gmail dot com)
 * 
 * openAUSIAS: The stunning micro-library that helps you to develop easily 
 *             AJAX web applications by using Java and jQuery
 * openAUSIAS is distributed under the MIT License (MIT)
 * Sources at https://github.com/rafaelaznar/openAUSIAS
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package net.daw.service.specific.implementation;

import java.sql.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.daw.service.generic.implementation.TableServiceGenImpl;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.daw.bean.specific.implementation.UsuarioBean;
import net.daw.connection.implementation.BoneConnectionPoolImpl;
import net.daw.connection.publicinterface.ConnectionInterface;
import net.daw.dao.specific.implementation.UsuarioDao;
import net.daw.helper.statics.ParameterCook;

public class UsuarioService extends TableServiceGenImpl {

    public UsuarioService(HttpServletRequest request) {
        super(request);
    }

    public void getFromLogin() {

        String op = ParameterCook.prepareOperation(oRequest);
        ConnectionInterface DataConnectionSource = null;
        Connection oConnection = null;

        try {

            if (op.equals("login")) {

                DataConnectionSource = new BoneConnectionPoolImpl();

                oConnection = DataConnectionSource.newConnection();

                UsuarioBean oUsuarioBean = new UsuarioBean();
                String login = oRequest.getParameter("login");
                String pass = oRequest.getParameter("password");
                
                oUsuarioBean.setLogin(login);
                oUsuarioBean.setPassword(pass);
                UsuarioDao oUsuarioDao = new UsuarioDao(oConnection);
                oUsuarioBean = oUsuarioDao.getFromLogin(oUsuarioBean);
                
                if (oUsuarioBean.getId() != 0) {
                                    //oUsuario = oUsuarioDao.type(oUsuario); //fill user level -> pending
                                    oRequest.getSession().setAttribute("userBean", oUsuarioBean);                
                }
            }else if(op.equals("logout")){
                oRequest.getSession().invalidate();
        }
        } catch (Exception ex) {
            Logger.getLogger(UsuarioService.class.getName()).log(Level.SEVERE, null, ex);

        }
        //return null;

    }

}
