package com.hemplant.demo.no_such_method_in_2_10

import android.app.Activity
import android.os.Bundle
import android.widget.TextView

/**
 * Created with IntelliJ IDEA.
 * User: taisukeoe
 * Date: 2013/03/12
 * Time: 15:26
 * To change this template use File | Settings | File Templates.
 */
class DemoActivity extends Activity{
  override def onCreate(savedInstanceState: Bundle) {
    super.onCreate(savedInstanceState)

    new SQLHelperDemo(this).getReadableDatabase.close()

    val textView = new TextView(this)
    textView.setText("Do I still survive?")
    setContentView(textView)
  }
}
