#include<jni.h> 
#include<stdio.h> 
#include "sampleDLL.h" 
JNIEXPORT jint JNICALL Java_sampleDLL_add(JNIEnv *env, jobject thisobj, jint num1, jint num2)
{ return num1+num2; } 
JNIEXPORT jint JNICALL Java_sampleDLL_sub(JNIEnv *env, jobject thisobj, jint num1, jint num2)
{ return num1-num2; 
} 
JNIEXPORT jint JNICALL Java_sampleDLL_mul(JNIEnv *env, jobject thisobj, jint num1, jint num2)
{ return num1*num2; } 
JNIEXPORT jint JNICALL Java_sampleDLL_sq(JNIEnv *env, jobject thisobj, jint num1, jint num2)
{
	return num1*num2;
}

