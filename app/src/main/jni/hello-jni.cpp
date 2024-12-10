#include <string.h>
#include <jni.h>

extern "C"
{
	JNIEXPORT jstring JNICALL Java_com_mycompany_myndkapp_HelloJni_stringFromJNI(JNIEnv* env, jobject thiz)
	{
		return env->NewStringUTF("Hello from JNI !");
	}
}
