package com.topsquad.shortenservice.service;

import com.topsquad.shortenservice.entity.ShortURL;
import com.topsquad.shortenservice.model.PreviousShortenResponse;
import com.topsquad.shortenservice.repo.ShortURLRepository;
import com.topsquad.shortenservice.util.CodeGeneratorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ShortenService {

    @Value("${auth-service-uri}")
    private String auth_host;

    @Autowired
    private RestTemplate template;

    @Autowired
    private ShortURLRepository shortURLRepo;

    public String shortenUrl(String url, String token) throws Exception {
        String createdId = template.getForObject(auth_host+"//api/auth/whosOwner?token=" + token, String.class);
        if(!url.startsWith("https://") || !url.startsWith("http://")){
            url = "http://"+url;
        }
        if(createdId == null){
            throw new Exception("This user not exist on the system!");
        }
        String code = CodeGeneratorUtil.generateRandomCode(6);

        ShortURL shortURL = new ShortURL();
        shortURL.setOriginalUrl(url);
        shortURL.setShortCode(code);
        shortURL.setCreated_at(new Date());
        shortURL.setCreatedById(Integer.parseInt(createdId));

        shortURLRepo.save(shortURL);
        return code;
    }

    public String lookUpUrl(String code){
        ShortURL shortURL = shortURLRepo.findByShortCode(code);
        return shortURL.getOriginalUrl();
    }

    public void removeUrl(Integer id){
        shortURLRepo.deleteById(id);
    }

    public List<PreviousShortenResponse> listPrevious(String token) throws Exception {
        String createdId = template.getForObject(auth_host+"//api/auth/whosOwner?token=" + token, String.class);
        if(createdId == null){
            throw new Exception("This user not exist on the system!");
        }

        List<ShortURL> listData = shortURLRepo.findAllByCreatedById(Integer.parseInt(createdId));
        if(listData == null){
            throw new Exception("Cannot find any data with this user");
        }

        List<PreviousShortenResponse> responseList = new ArrayList<>();
        for(ShortURL data: listData){
            PreviousShortenResponse shortenResponse = new PreviousShortenResponse();
            shortenResponse.setId(data.getId());
            shortenResponse.setOriginalUrl(data.getOriginalUrl());
            shortenResponse.setCreated_at(data.getCreated_at());
            shortenResponse.setShortCode(data.getShortCode());
            responseList.add(shortenResponse);
        }
        return responseList;
    }
}
