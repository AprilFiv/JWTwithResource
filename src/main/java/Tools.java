/**
 * Created by Administrator on 2017/8/6.
 */

import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.Map;

;

public interface Tools {
    public static String Create(Map<String, Object> headerMap, Map<String, Object> playloadMap, Algorithm algorithm) {
        Class c = null;
        try {
            c = Class.forName("com.auth0.jwt.JWTCreator");
            Constructor con = c.getDeclaredConstructor(Algorithm.class, Map.class, Map.class);
            con.setAccessible(true);
            JWTCreator jwtCreator = (JWTCreator) con.newInstance(algorithm, headerMap, playloadMap);
            Method method = c.getDeclaredMethod("sign");
            method.setAccessible(true);
            String token = (String) method.invoke(jwtCreator);
            return token;
        } catch (Exception e) {
            throw new JWTCreationException("Can't create JWT string", e);
        }
    }

    public static JsonWebToken Verify(String token, Algorithm algorithm) throws JWTDecodeException {
        String[] parts = token.split("\\.");
        if (parts.length == 2 && token.endsWith(".")) {
            parts = new String[]{parts[0], parts[1], ""};
        }
        if (parts.length != 3) {
            throw new JWTDecodeException(String.format("The token was expected to have 3 parts, but got %s.", new Object[]{Integer.valueOf(parts.length)}));
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append(parts[0]);
            sb.append(".");
            sb.append(parts[1]);
            byte[] signatureBytes = algorithm.sign(sb.toString().getBytes(StandardCharsets.UTF_8));
            String signature = Base64.encodeBase64URLSafeString(signatureBytes);
            if (!signature.equals(parts[2])) {
                throw new JWTDecodeException("The token is of wrong format");
            } else {
                String headerJson = StringUtils.newStringUtf8(Base64.decodeBase64(parts[0]));
                String playloadJson = StringUtils.newStringUtf8(Base64.decodeBase64(parts[1]));
                ObjectMapper mapper = new ObjectMapper();
                try {
                    Map headerMap = mapper.readValue(headerJson, Map.class);
                    Map playloadMap = mapper.readValue(playloadJson, Map.class);
                    return new JsonWebToken(parts[0], parts[1], parts[2], headerMap, playloadMap);
                } catch (IOException e) {
                    throw new JWTDecodeException("Something wrong occured");
                }

            }
        }
    }
}
