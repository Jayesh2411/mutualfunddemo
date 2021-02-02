import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.rmi.ConnectException;

public class ConnectionImpl implements Connection{

    public ConnectionImpl() {
        // TODO Auto-generated constructor stub
    }

    public URL getURLConnection(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.connect();
        int response = connection.getResponseCode();
        if (response != 200) {
            throw new ConnectException("Connection not established");
        }
        return url;
    }
}
