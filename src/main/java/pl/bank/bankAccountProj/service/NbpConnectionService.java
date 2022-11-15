package pl.bank.bankAccountProj.service;

import lombok.extern.slf4j.Slf4j;
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

    //@Cacheable("nbp_items")
    //@Retryable(backoff = @Backoff(maxDelay = 2000), include = {ResourceAccessException.class})
    public Double getTodaysTradePlnValue(String currency, String nbpApiUrl) {
        log.info("Getting nbp url using [nbpApiUrl = {}]", nbpApiUrl);
        String requestUrl = nbpApiUrl + currency;
        ResponseEntity<NBPExchangeDto> response = null;
        NBPExchangeDto body = null;
        try {
            log.info("Sending request to NBP service [request url = {}]", requestUrl);
            response = new RestTemplate().getForEntity(requestUrl, NBPExchangeDto.class);
            body = response.getBody();
            log.debug("Received body (getTodaysTradePlnValue) = [{}]", body);
        } catch (HttpClientErrorException hcee) {
            log.error("Request to NBP service ended with client error", hcee);
            throw new ApiException("400", "(0)", "statMessgInternalError");
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
