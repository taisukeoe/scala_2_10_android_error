This project is **to reproduce "NoSuchMethodError: android.database.sqlite.SQLiteClosable.close" in Scala 2.10.0 with android-16 or above enviroment**. 

This project works fine in Scala 2.9.3, 2.9.2, 2.9.1 or 2.9.0 with android-16, or 2.10.0 with android-15.

below devices reproduced:

 *  Xperia acro HD (Sony Ericsson) Android 4.0.4 IS12S 
 *  REGZA PHONE (Fujitsu Toshiba) Android 2.2.2 T-01C
 *	Optimus Chat (LG) Android 2.2.2 L-04C

Only below device does NOT reproduce this error even in 2.10.0 with android-16 or above.
 *	Galaxy NEXUS (Samsung) Android 4.1.1 

Full stack trace is as follows.

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