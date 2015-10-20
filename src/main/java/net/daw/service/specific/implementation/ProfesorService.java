/*
 * Copyright (c) 2015 by Rafael Angel Aznar Aparici (rafaaznar at gmail dot com)
 * 
 * openAUSIAS: The stunning micro-library that helps you to develop easily 
 * AJAX web applications by using Java and jQuery
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
 * 
 */
package net.daw.service.specific.implementation;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import net.daw.bean.specific.implementation.ProfesorBean;
import net.daw.connection.implementation.BoneConnectionPoolImpl;
import net.daw.dao.specific.implementation.ProfesorDao;
import net.daw.helper.statics.FilterBeanHelper;
import net.daw.helper.statics.ParameterCook;
import net.daw.service.generic.implementation.TableServiceGenImpl;

/**
 *
 * @author a020864288e
 */
public class ProfesorService extends TableServiceGenImpl {

    public ProfesorService(HttpServletRequest request) {
        super(request);
    }

    @Override
    public String get() throws Exception {

        int id = ParameterCook.prepareId(oRequest);

        Connection oConnection = new BoneConnectionPoolImpl().newConnection();

        ProfesorDao oProfesorDao = new ProfesorDao(oConnection);

        ProfesorBean oProfesorBean = new ProfesorBean();
        oProfesorBean.setId(id);

        oProfesorBean = oProfesorDao.get(oProfesorBean, 1);
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("dd/MM/yyyy");
        Gson gson = gsonBuilder.create();
        String data = gson.toJson(oProfesorBean);

        return data;
    }

    @Override
    public String getcount() throws Exception {

        Connection oConnection = new BoneConnectionPoolImpl().newConnection();

        ProfesorDao oProfesorDao = new ProfesorDao(oConnection);

        ProfesorBean oProfesorBean = new ProfesorBean();
         ArrayList<FilterBeanHelper> alFilterBeanHelper = ParameterCook.prepareFilter(oRequest);
        //  ArrayList<FilterBeanHelper> alFilter = new ArrayList<FilterBeanHelper>();
        int conta = oProfesorDao.getCount(alFilterBeanHelper/*alFilter*/);

        String data = "{\"data\":\"" + Integer.toString(conta) + "\"}";

        return data;
    }

    @Override
    public String getall() throws Exception {

        Connection oConnection = new BoneConnectionPoolImpl().newConnection();

        ProfesorDao oProfesorDao = new ProfesorDao(oConnection);
        ArrayList<ProfesorBean> alProfesorBean = new ArrayList<ProfesorBean>();
        ArrayList<FilterBeanHelper> alFilterBeanHelper = ParameterCook.prepareFilter(oRequest);
        HashMap<String, String> hmOrder = ParameterCook.prepareOrder(oRequest);
        
        alProfesorBean = oProfesorDao.getAll(alFilterBeanHelper, hmOrder);
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("dd/MM/yyyy");
        Gson gson = gsonBuilder.create();
        String data = gson.toJson(alProfesorBean);

        return data;
    }

    public String getpages() throws Exception {

        Connection oConnection = new BoneConnectionPoolImpl().newConnection();

        ProfesorDao oProfesorDao = new ProfesorDao(oConnection);
        int intRegsPerPag = ParameterCook.prepareRpp(oRequest);
        ArrayList<FilterBeanHelper> alFilterBeanHelper = ParameterCook.prepareFilter(oRequest);
        int cont = oProfesorDao.getPages(intRegsPerPag, alFilterBeanHelper);
        
        
        String data = "{\"data\":\"" + Integer.toString(cont) + "\"}";

        return data;
    }

    public String getpage() throws Exception {

        Connection oConnection = new BoneConnectionPoolImpl().newConnection();

        ProfesorDao oProfesorDao = new ProfesorDao(oConnection);

        ArrayList<ProfesorBean> alProfesorBean = new ArrayList<ProfesorBean>();

        int intRegsPerPag = ParameterCook.prepareRpp(oRequest);
        int intPage = ParameterCook.preparePage(oRequest);
        ArrayList<FilterBeanHelper> alFilterBeanHelper = ParameterCook.prepareFilter(oRequest);
        HashMap<String, String> hmOrder = ParameterCook.prepareOrder(oRequest);
        alProfesorBean = oProfesorDao.getPage(intRegsPerPag, intPage, alFilterBeanHelper, hmOrder);
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("dd/MM/yyyy");
        Gson gson = gsonBuilder.create();
        String data = gson.toJson(alProfesorBean);

        return data;
    }

    /* 
     */
}

