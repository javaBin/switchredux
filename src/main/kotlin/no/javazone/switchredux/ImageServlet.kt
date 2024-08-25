package no.javazone.switchredux

import com.dropbox.core.DbxRequestConfig
import com.dropbox.core.v2.DbxClientV2
import java.io.InputStream
import javax.servlet.ServletException
import javax.servlet.ServletOutputStream
import javax.servlet.http.*


class ImageServlet: HttpServlet() {
    private val config = DbxRequestConfig.newBuilder("SwitchDropbox").build()
    private val client:DbxClientV2? = SetupValue.DROPBOX_ACCESS.valueOrNull()?.let {   DbxClientV2(config, it)}

    @Throws(ServletException::class)
    override fun doGet(req: HttpServletRequest, resp: HttpServletResponse) {
        if (client == null) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Images not setup")
            return
        }
        val path = req.pathInfo ?: return
        try {
            val imageInputStream = client.files().download(path).inputStream
            imageInputStream.use { inputStream ->
                resp.contentType = "image/jpeg"
                resp.status = HttpServletResponse.SC_OK
                writeInputStreamToOutputStream(inputStream, resp.outputStream)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Image not found.")
        }
    }

    private fun writeInputStreamToOutputStream(input: InputStream, output: ServletOutputStream) {
        val buffer = ByteArray(4096)
        var bytesRead: Int
        while (input.read(buffer).also { bytesRead = it } != -1) {
            output.write(buffer, 0, bytesRead)
        }
    }
}