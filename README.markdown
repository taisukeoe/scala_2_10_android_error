This project is **to reproduce unexpected "NoSuchMethodError: android.database.sqlite.SQLiteClosable.close" in Scala 2.10.x with android-16 (that means Androd 4.1) or above enviroment** that seems to come from bytecode difference between Scala 2.10.x and Scala 2.9.x. 

###Problem

This very straight forward Android project source code throws NoSuchMethodError :android.database.sqlite.SQLiteClosable.close .

This is because *"new SQLHelperDemo(this).getReadableDatabase.close()"* in android-16 has been compiled to *SQLiteClosable.close()* in bytecodes by Scala 2.10.x that doesn't exist in android-15. It should be *SQLiteDatabase.close()* at least in this case. Please find below javap results in Scala2.9.x and Scala2.10.x.

For your info, SQLiteDatabase starts to inherit "close()" method from SQLiteClosable at android-16, while SQLiteDatabase implemented "close()" method without any inheritance until android-15.

####Project whole souce codes:

	class DemoActivity extends Activity{
	  override def onCreate(savedInstanceState: Bundle) {
    	super.onCreate(savedInstanceState)

	    new SQLHelperDemo(this).getReadableDatabase.close()

    	val textView = new TextView(this)
	    textView.setText("Do I still survive?")
    	setContentView(textView)
  		}
	}
	class SQLHelperDemo(context:Context) extends SQLiteOpenHelper(context,"demo",null,1){
	  def onCreate(p1: SQLiteDatabase) {}
	  def onUpgrade(p1: SQLiteDatabase, p2: Int, p3: Int) {}
	}


####Full stack traces:

	W/dalvikvm(7951): threadid=1: thread exiting with uncaught exception (group=0x40abd210)
	E/AndroidRuntime(7951): FATAL EXCEPTION: main
	E/AndroidRuntime(7951): java.lang.NoSuchMethodError: android.database.sqlite.SQLiteClosable.close
	E/AndroidRuntime(7951): 	at com.hemplant.demo.no_such_method_in_2_10.DemoActivity.onCreate(DemoActivity.scala:18)
	E/AndroidRuntime(7951): 	at android.app.Activity.performCreate(Activity.java:4465)
	E/AndroidRuntime(7951): 	at android.app.Instrumentation.callActivityOnCreate(Instrumentation.java:1049)
	E/AndroidRuntime(7951): 	at android.app.ActivityThread.performLaunchActivity(ActivityThread.java:1931)
	E/AndroidRuntime(7951): 	at android.app.ActivityThread.handleLaunchActivity(ActivityThread.java:1992)
	E/AndroidRuntime(7951): 	at android.app.ActivityThread.access$600(ActivityThread.java:127)
	E/AndroidRuntime(7951): 	at android.app.ActivityThread$H.handleMessage(ActivityThread.java:1158)
	E/AndroidRuntime(7951): 	at android.os.Handler.dispatchMessage(Handler.java:99)
	E/AndroidRuntime(7951): 	at android.os.Looper.loop(Looper.java:137)
	E/AndroidRuntime(7951): 	at android.app.ActivityThread.main(ActivityThread.java:4441)
	E/AndroidRuntime(7951): 	at java.lang.reflect.Method.invokeNative(Native Method)
	E/AndroidRuntime(7951): 	at java.lang.reflect.Method.invoke(Method.java:511)
	E/AndroidRuntime(7951): 	at com.android.internal.os.ZygoteInit$MethodAndArgsCaller.run(ZygoteInit.java:823)
	E/AndroidRuntime(7951): 	at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:590)
	E/AndroidRuntime(7951): 	at dalvik.system.NativeStart.main(Native Method)
	
	
	byte code difference.
	
**Scala 2.10.0 android-16(Android4.1) bytecodes:**

	>javap -c DemoActivitypublic

	class com.hemplant.demo.no_such_method_in_2_10.DemoActivity extends android.app.Activity{
	public com.hemplant.demo.no_such_method_in_2_10.DemoActivity();
  	Code:
   	0:aload_0
   	1:invokespecial#8; //Method android/app/Activity."<init>":()V
   	4:return

	public void onCreate(android.os.Bundle);
  	Code:
   	0:aload_0
   	1:aload_1
   	2:invokespecial#12; //Method android/app/Activity.onCreate:(Landroid/os/Bundle;)V
   	5:new#14; //class com/hemplant/demo/no_such_method_in_2_10/SQLHelperDemo
   	8:dup
   	9:aload_0
   	10:invokespecial#17; //Method com/hemplant/demo/no_such_method_in_2_10/SQLHelperDemo."<init>":(Landroid/content/Context;)V
   	13:invokevirtual#23; //Method android/database/sqlite/SQLiteOpenHelper.getReadableDatabase:()Landroid/database/sqlite/SQLiteDatabase;
   	16:astore_2
   	17:new#25; //class android/widget/TextView
   	20:dup
   	21:aload_0
   	22:invokespecial#26; //Method android/widget/TextView."<init>":(Landroid/content/Context;)V
   	25:astore_3
   	26:aload_3
   	27:new#28; //class scala/collection/mutable/StringBuilder
   	30:dup
   	31:invokespecial#29; //Method scala/collection/mutable/StringBuilder."<init>":()V
   	34:ldc#31; //String Do I still survive?
   	36:invokevirtual#35; //Method scala/collection/mutable/StringBuilder.append:(Ljava/lang/Object;)Lscala/collection/mutable/StringBuilder;
   	39:aload_2
   	40:invokevirtual#41; //Method android/database/sqlite/SQLiteDatabase.toString:()Ljava/lang/String;
   	43:invokevirtual#35; //Method scala/collection/mutable/StringBuilder.append:(Ljava/lang/Object;)Lscala/collection/mutable/StringBuilder;
   	46:invokevirtual#42; //Method scala/collection/mutable/StringBuilder.toString:()Ljava/lang/String;
   	49:invokevirtual#46; //Method android/widget/TextView.setText:(Ljava/lang/CharSequence;)V
   	52:aload_0
   	53:aload_3
   	54:invokevirtual#50; //Method android/app/Activity.setContentView:(Landroid/view/View;)V
   	57:aload_2
   	**58:invokevirtual#55; //Method android/database/sqlite/SQLiteClosable.close:()V**
   	61:return
	}

**Scala2.10.x with android-15, or Scala2.9.x with android-16 bytecodes:**

	>javap -c DemoActivity

	public class com.hemplant.demo.no_such_method_in_2_10.DemoActivity extends android.app.Activity{
	public com.hemplant.demo.no_such_method_in_2_10.DemoActivity();
	  Code:
	   0:aload_0
   	1:invokespecial#8; //Method android/app/Activity."<init>":()V
   	4:return

	public void onCreate(android.os.Bundle);
  	Code:
   	0:aload_0
   	1:aload_1
   	2:invokespecial#12; //Method android/app/Activity.onCreate:(Landroid/os/Bundle;)V
	   5:new#14; //class com/hemplant/demo/no_such_method_in_2_10/SQLHelperDemo
   	8:dup
   	9:aload_0
   	10:invokespecial#17; //Method com/hemplant/demo/no_such_method_in_2_10/SQLHelperDemo."<init>":(Landroid/content/Context;)V
   	13:invokevirtual#23; //Method android/database/sqlite/SQLiteOpenHelper.getReadableDatabase:()Landroid/database/sqlite/SQLiteDatabase;
   	16:astore_2
   	17:new#25; //class android/widget/TextView
  	20:dup
   	21:aload_0
   	22:invokespecial#26; //Method android/widget/TextView."<init>":(Landroid/content/Context;)V
   	25:astore_3
   	26:aload_3
   	27:new#28; //class scala/collection/mutable/StringBuilder
   	30:dup
   	31:invokespecial#29; //Method scala/collection/mutable/StringBuilder."<init>":()V
   	34:ldc#31; //String Do I still survive?
   	36:invokevirtual#35; //Method scala/collection/mutable/StringBuilder.append:(Ljava/lang/Object;)Lscala/collection/mutable/StringBuilder;
   	39:aload_2
   	40:invokevirtual#41; //Method java/lang/Object.toString:()Ljava/lang/String;
   	43:invokevirtual#35; //Method scala/collection/mutable/StringBuilder.append:(Ljava/lang/Object;)Lscala/collection/mutable/StringBuilder;
   	46:invokevirtual#42; //Method scala/collection/mutable/StringBuilder.toString:()Ljava/lang/String;
   	49:invokevirtual#46; //Method android/widget/TextView.setText:(Ljava/lang/CharSequence;)V
   	52:aload_0
   	53:aload_3
   	54:invokevirtual#50; //Method android/app/Activity.setContentView:(Landroid/view/View;)V
   	57:aload_2
   	**58:invokevirtual#55; //Method android/database/sqlite/SQLiteDatabase.close:()V**
   	61:return
	}


Of note, this project works fine without NoSuchMethodError in Scala 2.9.x with android-16, or 2.10.x with android-15.

Below devices reproduce this problem:

 *  Xperia acro HD (Sony Ericsson) Android 4.0.4 IS12S 
 *  REGZA PHONE (Fujitsu Toshiba) Android 2.2.2 T-01C
 *	Optimus Chat (LG) Android 2.2.2 L-04C

Only below device does NOT reproduce this error even in 2.10.0 with android-16 or above. This is natural sinceSQLiteClosable has "close()" method from Android 4.1 .
 *	Galaxy NEXUS (Samsung) Android 4.1.1 