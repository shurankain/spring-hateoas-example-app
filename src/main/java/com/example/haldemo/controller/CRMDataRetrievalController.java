package com.example.haldemo.controller;

import com.example.haldemo.model.ClientDataDto;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping(value = "/clientData")
@Validated
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class CRMDataRetrievalController {

    // produces = "application/prs.hal-forms+json"
    @GetMapping(value = "/contact/ubs-participants")
    public ResponseEntity<EntityModel<ClientDataDto>> getPartnerInfoByPartnerIdOrBankingId(@RequestParam(name = "partnerId", required = false) String partnerId,
                                                                                           @RequestParam(name = "bankingRelationId", required = false) String bankingRelationId) {

        CRMDataRetrievalController crmDataRetrievalController = WebMvcLinkBuilder.methodOn(CRMDataRetrievalController.class);
        Link link= WebMvcLinkBuilder.linkTo(crmDataRetrievalController.getPartnerInfoByPartnerIdOrBankingId(partnerId, bankingRelationId))
                .withSelfRel()
                .andAffordance(WebMvcLinkBuilder.afford(WebMvcLinkBuilder.methodOn(CRMDataRetrievalController.class)
                        .getPartnerInfoByPartnerIdOrBankingId(null, null)));

        Optional<ClientDataDto> clientData = Optional.of(constructClientDataDto());

        return clientData.map(data -> new ResponseEntity<>(EntityModel.of(clientData.get(), link), HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    private ClientDataDto constructClientDataDto() {
        return ClientDataDto.builder()
                .partnerId("partnerId")
                .partnerFullName("partnerFullName")
                .language("language")
                .build();
    }
}
