package com.hemplant.demo.no_such_method_in_2_10

import android.content.Context
import android.database.sqlite.{SQLiteDatabase, SQLiteOpenHelper}

/**
 * Created with IntelliJ IDEA.
 * User: taisukeoe
 * Date: 2013/03/12
 * Time: 15:27
 * To change this template use File | Settings | File Templates.
 */
class SQLHelperDemo(context:Context) extends SQLiteOpenHelper(context,"demo",null,1){

  def onCreate(p1: SQLiteDatabase) {
  }

  def onUpgrade(p1: SQLiteDatabase, p2: Int, p3: Int) {

  }
}
