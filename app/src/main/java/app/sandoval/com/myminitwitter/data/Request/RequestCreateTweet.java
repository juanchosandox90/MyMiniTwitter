
package app.sandoval.com.myminitwitter.data.Request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestCreateTweet {

    @SerializedName("mensaje")
    @Expose
    private String mensaje;

    public RequestCreateTweet(){

    }

    public RequestCreateTweet(String mensaje) {
        this.mensaje = mensaje;

    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

}
