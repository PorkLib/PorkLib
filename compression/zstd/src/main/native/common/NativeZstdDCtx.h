/* DO NOT EDIT THIS FILE - it is machine generated */
//actually it's not, it was initially though
//easier to make this by hand lol
#include <jni.h>

#ifndef _Included_net_daporkchop_lib_compression_zstd_natives_NativeZstdDCtx
#define _Included_net_daporkchop_lib_compression_zstd_natives_NativeZstdDCtx

#ifdef __cplusplus
extern "C" {
#endif

/*
 * Class:     net_daporkchop_lib_compression_zstd_natives_NativeZstdDCtx
 * Method:    allocateCtx
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_net_daporkchop_lib_compression_zstd_natives_NativeZstdDCtx_allocateCtx
  (JNIEnv *, jclass);

/*
 * Class:     net_daporkchop_lib_compression_zstd_natives_NativeZstdDCtx
 * Method:    releaseCtx
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_net_daporkchop_lib_compression_zstd_natives_NativeZstdDCtx_releaseCtx
  (JNIEnv *, jclass, jlong);

/*
 * Class:     net_daporkchop_lib_compression_zstd_natives_NativeZstdDCtx
 * Method:    doDecompressNoDict
 * Signature: (JJIJI)I
 */
JNIEXPORT jint JNICALL Java_net_daporkchop_lib_compression_zstd_natives_NativeZstdDCtx_doDecompressNoDict
  (JNIEnv *, jobject, jlong, jlong, jint, jlong, jint);

/*
 * Class:     net_daporkchop_lib_compression_zstd_natives_NativeZstdDCtx
 * Method:    doDecompressRawDict
 * Signature: (JJIJIJI)I
 */
JNIEXPORT jint JNICALL Java_net_daporkchop_lib_compression_zstd_natives_NativeZstdDCtx_doDecompressRawDict
  (JNIEnv *, jobject, jlong, jlong, jint, jlong, jint, jlong, jint);

/*
 * Class:     net_daporkchop_lib_compression_zstd_natives_NativeZstdDCtx
 * Method:    doDecompressCDict
 * Signature: (JJIJIJ)I
 */
JNIEXPORT jint JNICALL Java_net_daporkchop_lib_compression_zstd_natives_NativeZstdDCtx_doDecompressCDict
  (JNIEnv *, jobject, jlong, jlong, jint, jlong, jint, jlong);

#ifdef __cplusplus
}
#endif

#endif //_Included_net_daporkchop_lib_compression_zstd_natives_NativeZstdDCtx