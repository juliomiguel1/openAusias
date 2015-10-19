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
package net.daw.dao.specific.implementation;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import net.daw.bean.specific.implementation.ProfesorBean;
import net.daw.dao.generic.implementation.TableDaoGenImpl;
import net.daw.data.specific.implementation.MysqlDataSpImpl;
import net.daw.helper.statics.AppConfigurationHelper;
import net.daw.helper.statics.FilterBeanHelper;

/**
 *
 * @author a020864288e
 */
public class ProfesorDao extends TableDaoGenImpl<ProfesorBean>  {

    public ProfesorDao(Connection pooledConnection) throws Exception {
        super(pooledConnection);
    }
  
  
  public ProfesorBean get(ProfesorBean oProfesorBean, Integer expand) throws Exception {
      MysqlDataSpImpl oMysql = new MysqlDataSpImpl(oConnection);
            if(oProfesorBean.getId()>0){
                
               if(oMysql.existsOne(strSqlSelectDataOrigin, oProfesorBean.getId())){
                   oProfesorBean.setNombre(oMysql.getOne(strSqlSelectDataOrigin, "nombre", oProfesorBean.getId()));
                   oProfesorBean.setEstado(oMysql.getOne(strSqlSelectDataOrigin, "estado", oProfesorBean.getId()));
               }                
            }      
      try {
            
            return oProfesorBean;
        } catch (Exception e) {
            throw new Exception(this.getClass().getName() + ":get ERROR: " + e.getMessage());
        }
    }
  
   @Override
  public ArrayList<ProfesorBean> getAll(ArrayList<FilterBeanHelper> alFilter, HashMap<String, String> hmOrder) throws Exception{
        
      MysqlDataSpImpl oMysql = new MysqlDataSpImpl(oConnection);
      ArrayList<ProfesorBean> alProfesorBean = new ArrayList<>();
     
      try{
          ResultSet result = oMysql.getAllSql(strSqlSelectDataOrigin);
          if(result!= null){
            while (result.next()) {
                   ProfesorBean oProfesorBean = new ProfesorBean();

                   oProfesorBean.setId(result.getInt("id"));
                   oProfesorBean.setNombre(result.getString("nombre"));
                   oProfesorBean.setEstado(result.getString("estado"));
                   alProfesorBean.add(oProfesorBean);
            }
          }
          
      } catch (Exception ex) {
           throw new Exception(this.getClass().getName() + ":get ERROR: " + ex.getMessage());
        }
     
         
     return alProfesorBean;
  }
  
    @Override
  public int getCount(ArrayList<FilterBeanHelper> alFilter) throws Exception{
  
      MysqlDataSpImpl oMysql = new MysqlDataSpImpl(oConnection);
      
      int numero = oMysql.getCount(strSqlSelectDataOrigin);
      return numero;
  }
}
