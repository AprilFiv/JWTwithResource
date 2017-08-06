import java.io.Serializable;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/6.
 */
public class JsonWebToken implements Serializable {
    private String header;
    private String playload;
    private String signature;
    private Map headerMap;
    private Map playloadMap;

    public JsonWebToken(String header, String playload, String signature) {
        this.header = header;
        this.playload = playload;
        this.signature = signature;
    }

    public JsonWebToken(String header, String playload, String signature, Map headerMap, Map playloadMap) {
        this.header = header;
        this.playload = playload;
        this.signature = signature;
        this.headerMap = headerMap;
        this.playloadMap = playloadMap;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getPlayload() {
        return playload;
    }

    public void setPlayload(String playload) {
        this.playload = playload;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public Map getHeaderMap() {
        return headerMap;
    }

    public void setHeaderMap(Map headerMap) {
        this.headerMap = headerMap;
    }

    public Map getPlayloadMap() {
        return playloadMap;
    }

    public void setPlayloadMap(Map playloadMap) {
        this.playloadMap = playloadMap;
    }

    @Override
    public String toString() {
        return header + "." + playload + "." + signature;
    }
}
