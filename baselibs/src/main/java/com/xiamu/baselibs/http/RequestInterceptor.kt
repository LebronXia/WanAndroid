//package com.xiamu.baselibs.http
//
//import java.io.IOException
//import java.io.UnsupportedEncodingException
//import java.net.URLDecoder
//import java.nio.charset.Charset
//import java.util.Locale
//import java.util.concurrent.TimeUnit
//
//import javax.inject.Inject
//import javax.inject.Singleton
//
//import me.xiaobailong24.mvvmarms.repository.utils.ZipHelper
//import okhttp3.Interceptor
//import okhttp3.MediaType
//import okhttp3.Request
//import okhttp3.RequestBody
//import okhttp3.Response
//import okhttp3.ResponseBody
//import okio.Buffer
//import okio.BufferedSource
//import timber.log.Timber
//
//import timber.log.Timber.w
//
///**
// * @author xiaobailong24
// * @date 2017/6/16
// * Http 请求/响应拦截器
// */
//class RequestInterceptor
//constructor(@param:Nullable private val mHandler: GlobalHttpHandler?, @Nullable level: Level?) :
//    Interceptor {
//    private val printLevel: Level
//
//    enum class Level {
//        /**
//         * 不打印log
//         */
//        NONE,
//
//        /**
//         * 只打印请求信息
//         */
//        REQUEST,
//
//        /**
//         * 只打印响应信息
//         */
//        RESPONSE,
//
//        /**
//         * 所有数据全部打印
//         */
//        ALL
//    }
//
//    init {
//        if (level == null) {
//            printLevel = Level.ALL
//        } else {
//            printLevel = level
//        }
//    }
//
//
//    /**
//     * Response 拦截
//     *
//     * @param chain Chain
//     * @return Response
//     * @throws IOException IOException
//     */
//    @Throws(IOException::class)
//    override fun intercept(@NonNull chain: Interceptor.Chain): Response {
//        val request = chain.request()
//
//        val logRequest =
//            printLevel == Level.ALL || printLevel != Level.NONE && printLevel == Level.REQUEST
//
//        if (logRequest) {
//            val hasRequestBody = request.body() != null
//            //打印请求信息
//            Timber.w(
//                "HTTP REQUEST >>>%n 「 %s 」%nParams : 「 %s 」%nConnection : 「 %s 」%nHeaders : %n「 %s 」",
//                getTag(request),
//                if (hasRequestBody) parseParams(request.newBuilder().build().body()!!) else "Null",
//                chain.connection(),
//                request.headers()
//            )
//        }
//
//        val logResponse =
//            printLevel == Level.ALL || printLevel != Level.NONE && printLevel == Level.RESPONSE
//
//        val t1 = if (logResponse) System.nanoTime() else 0
//        val originalResponse: Response
//        try {
//            originalResponse = chain.proceed(request)
//        } catch (e: Exception) {
//            w("Http Error: $e")
//            throw e
//        }
//
//        val t2 = if (logResponse) System.nanoTime() else 0
//
//        if (logResponse) {
//            val bodySize = if (originalResponse.body()!!.contentLength() != -1)
//                originalResponse.body()!!.contentLength().toString() + "-byte"
//            else
//                "unknown-length"
//            //打印响应时间以及响应头
//            Timber.w(
//                "HTTP RESPONSE in [ %d-ms ] , [ %s ] >>>%n%s",
//                TimeUnit.NANOSECONDS.toMillis(t2 - t1), bodySize, originalResponse.headers()
//            )
//        }
//
//        //打印响应结果
//        val bodyString = printResult(request, originalResponse.newBuilder().build(), logResponse)
//
//        //这里可以比客户端提前一步拿到服务器返回的结果,可以做一些操作,比如token超时,重新获取
//        return if (mHandler != null) {
//            mHandler!!.onHttpResultResponse(bodyString, chain, originalResponse)
//        } else originalResponse
//
//    }
//
//    /**
//     * 打印响应结果
//     *
//     * @param request     Request
//     * @param response    Response
//     * @param logResponse 是否打印
//     * @return String
//     * @throws IOException IOException
//     */
//    @Nullable
//    @Throws(IOException::class)
//    private fun printResult(request: Request, response: Response, logResponse: Boolean): String? {
//        //读取服务器返回的结果
//        val responseBody = response.body()
//        var bodyString: String? = null
//        if (isParseable(responseBody!!.contentType())) {
//            try {
//                val source = responseBody.source()
//                // Buffer the entire body.
//                source.request(java.lang.Long.MAX_VALUE)
//                val buffer = source.buffer()
//
//                //获取content的压缩类型
//                val encoding = response
//                    .headers()
//                    .get("Content-Encoding")
//
//                val clone = buffer.clone()
//
//
//                //解析response content
//                bodyString = parseContent(responseBody, encoding, clone)
//            } catch (e: IOException) {
//                e.printStackTrace()
//            }
//
//            if (logResponse) {
//                Timber.w(
//                    "HTTP RESPONSE >>>%n「 %s 」%nResponse Content:%n%s",
//                    getTag(request),
//                    if (isJson(responseBody.contentType()!!))
//                        CharacterHandler.jsonFormat(bodyString)
//                    else if (isXml(responseBody.contentType()!!))
//                        CharacterHandler.xmlFormat(bodyString)
//                    else
//                        bodyString
//                )
//            }
//
//        } else {
//            if (logResponse) {
//                Timber.w(
//                    "HTTP RESPONSE >>>%n「 %s 」%n%s",
//                    getTag(request),
//                    "This result isn't parsed"
//                )
//            }
//        }
//        return bodyString
//    }
//
//
//    private fun getTag(request: Request): String {
//        return String.format(" [%s] 「 %s 」", request.method(), request.url().toString())
//    }
//
//
//    /**
//     * 解析服务器响应的内容
//     *
//     * @param responseBody ResponseBody
//     * @param encoding     编码
//     * @param clone        Buffer
//     * @return String
//     */
//    private fun parseContent(responseBody: ResponseBody, encoding: String?, clone: Buffer): String {
//        var charset: Charset? = Charset.forName("UTF-8")
//        val contentType = responseBody.contentType()
//        if (contentType != null) {
//            charset = contentType.charset(charset)
//        }
//        //content 使用 gzip 压缩
//        return if (encoding != null && encoding.equals("gzip", ignoreCase = true)) {
//            //解压
//            ZipHelper.decompressForGzip(clone.readByteArray(), convertCharset(charset!!))
//            //content 使用 zlib 压缩
//        } else if (encoding != null && encoding.equals("zlib", ignoreCase = true)) {
//            //解压
//            ZipHelper.decompressToStringForZlib(clone.readByteArray(), convertCharset(charset!!))
//        } else {
//            //content没有被压缩
//            clone.readString(charset!!)
//        }
//    }
//
//    companion object {
//
//        /**
//         * 解析请求服务器的请求参数
//         *
//         * @param body RequestBody
//         * @return String
//         * @throws UnsupportedEncodingException UnsupportedEncodingException
//         */
//        @Throws(UnsupportedEncodingException::class)
//        fun parseParams(body: RequestBody): String {
//            if (isParseable(body.contentType())) {
//                try {
//                    val requestBuffer = Buffer()
//                    body.writeTo(requestBuffer)
//                    var charset: Charset? = Charset.forName("UTF-8")
//                    val contentType = body.contentType()
//                    if (contentType != null) {
//                        charset = contentType.charset(charset)
//                    }
//                    return URLDecoder.decode(
//                        requestBuffer.readString(charset!!),
//                        convertCharset(charset)
//                    )
//
//                } catch (e: IOException) {
//                    e.printStackTrace()
//                }
//
//            }
//            return "This params isn't parsed"
//        }
//
//        /**
//         * 是否可以解析
//         *
//         * @param mediaType MediaType
//         * @return 是否可以解析
//         */
//        fun isParseable(mediaType: MediaType?): Boolean {
//            return mediaType != null && (mediaType.toString().toLowerCase(Locale.ROOT).contains("text")
//                    || isJson(mediaType)
//                    || isForm(mediaType)
//                    || isHtml(mediaType)
//                    || isXml(mediaType))
//        }
//
//        fun isJson(mediaType: MediaType): Boolean {
//            return mediaType.toString().toLowerCase(Locale.ROOT).contains("json")
//        }
//
//        fun isXml(mediaType: MediaType): Boolean {
//            return mediaType.toString().toLowerCase(Locale.ROOT).contains("xml")
//        }
//
//        fun isHtml(mediaType: MediaType): Boolean {
//            return mediaType.toString().toLowerCase(Locale.ROOT).contains("html")
//        }
//
//        fun isForm(mediaType: MediaType): Boolean {
//            return mediaType.toString().toLowerCase(Locale.ROOT).contains("x-www-form-urlencoded")
//        }
//
//        fun convertCharset(charset: Charset): String {
//            val s = charset.toString()
//            val i = s.indexOf("[")
//            return if (i == -1) {
//                s
//            } else s.substring(i + 1, s.length - 1)
//        }
//    }
//}
