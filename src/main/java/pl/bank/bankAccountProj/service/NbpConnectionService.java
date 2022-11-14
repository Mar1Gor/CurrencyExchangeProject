package pl.bank.bankAccountProj.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import pl.bank.bankAccountProj.dto.NBPExchangeDto;
import pl.bank.bankAccountProj.exception.ApiException;

@Slf4j
@Service
public class NbpConnectionService {

    @Value("${nbpApiUrl:https://api.nbp.pl/api/exchangerates/rates/a/}")
    private String nbpApiUrl;

    @Autowired
    private RestTemplate restTemplate;

    //@Cacheable("nbp_items")
    //@Retryable(backoff = @Backoff(maxDelay = 2000), include = {ResourceAccessException.class})
    public Double getTodaysTradePlnValue(String currency) {
        log.info("Getting nbp url using [nbpApiUrl = {}]", nbpApiUrl);
        String requestUrl = nbpApiUrl + currency;
        ResponseEntity<NBPExchangeDto> response = null;
        int status = 0;
        NBPExchangeDto body = null;

        try {
            log.info("Sending request to NBP service [request url = {}]", requestUrl);
            response = restTemplate.exchange(requestUrl, HttpMethod.GET, null, new ParameterizedTypeReference<NBPExchangeDto>() {
            });
            status = response.getStatusCodeValue();
            body = response.getBody();

        } catch (HttpClientErrorException hcee) {
            log.error("Request to NBP service ended with client error", hcee);
            status = hcee.getRawStatusCode();
        } catch (HttpServerErrorException hsee) {
            log.error("Request to NBP service ended with server error", hsee);
            throw new ApiException("500", "(1)", "statMessgInternalError");
        } catch (Exception ex) {
            log.error("Request to NBP service ended with unknown error", ex);
            throw new ApiException("500", "(2)", "statMessgInternalError");
        }
        log.info("Request to NBP service completed, response = [{}]", response);
        return body != null ? body.getRates().stream().findFirst().get().getMid() : 0;

    }
}
