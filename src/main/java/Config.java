package com.yuin.controller;

import com.auth0.jwt.algorithms.Algorithm;
import com.yuin.BaseAlgorithm;
import com.yuin.Tools;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

/**
 * Created by Administrator on 2017/8/6.
 */
@Component
public class Config implements BaseAlgorithm {
    private  Algorithm algorithm =null;

    public Config() {
        init();
    }

    @Override
    public void init() {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(1024);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
            RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
            algorithm = Algorithm.RSA256(publicKey, privateKey);
            System.out.println(Tools.Create(null,null,algorithm));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Algorithm getAlgorithm() {
        return algorithm;
    }
}
