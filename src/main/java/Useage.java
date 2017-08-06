import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/6.
 */
@Component
public class Useage {

    public static void main(String[] args) throws UnsupportedEncodingException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {

        Algorithm algorithm = null;

        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(1024);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
            RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
            algorithm = Algorithm.RSA256(publicKey, privateKey);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }


        Map<String, Object> header = new HashMap<>();
        Map<String, Object> playlord = new HashMap<>();
        header.put("alg", algorithm.getName());
        header.put("typ", "JWT");
        playlord.put("kid", algorithm.getSigningKeyId());
        String token = Tools.Create(header, playlord, algorithm);
        System.out.println(token);
        JsonWebToken jwt = Tools.Verify(token, algorithm);
        Map head = jwt.getHeaderMap();
        Map play = jwt.getPlayloadMap();
        System.out.println(head.get("typ"));

    }
}
