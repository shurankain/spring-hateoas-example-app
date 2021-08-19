package com.example.haldemo.controller;

import com.example.haldemo.model.ClientDataDto;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.QueryParameter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.mediatype.Affordances;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/clientData")
@Validated
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class CRMDataRetrievalController {

    // unused method created to test HAL
    @PutMapping(value = "/contact/ubs-participants/{id}")
    public ResponseEntity<ClientDataDto> changePartner(@PathVariable(name = "id", required = false) String id) {
        Optional<ClientDataDto> clientData = Optional.of(constructClientDataDto());

        return new ResponseEntity<>(clientData.get(), HttpStatus.OK);
    }

    // unused method created to test HAL
    @DeleteMapping(value = "/contact/ubs-participants/{id}")
    public ResponseEntity<Object> deletePartner(@PathVariable(name = "id") String id) {
        return ResponseEntity.ok().build();
    }

    // produces = "application/prs.hal-forms+json"
    @GetMapping(value = "/contact/some-participants")
    public ResponseEntity<CollectionModel<ClientDataDto>> getPartnerInfoByPartnerIdOrBankingId(@RequestParam(name = "partnerId", required = false) String partnerId,
                                                                                               @RequestParam(name = "bankingRelationId", required = false) String bankingRelationId) {

        CRMDataRetrievalController crmDataRetrievalController = methodOn(CRMDataRetrievalController.class);
        Link link = Affordances.of(linkTo(crmDataRetrievalController.getPartnerInfoByPartnerIdOrBankingId(partnerId, bankingRelationId)).withSelfRel())
                .afford(HttpMethod.GET)
                .withOutput(ClientDataDto.class) //
                .withName("getPartnerInfoByPartnerIdOrBankingId")
                .addParameters(
                        QueryParameter.optional("partnerId"),
                        QueryParameter.optional("bankingRelationId")
                )
                .andAfford(HttpMethod.POST)
                .withOutput(ClientDataDto.class)
                .withName("changePartner")
                .addParameters(
                        QueryParameter.required("id")
                )
                .andAfford(HttpMethod.DELETE)
                .withOutput(Object.class)
                .withName("deletePartner")
                .addParameters(
                        QueryParameter.required("id")
                )
                .toLink();

        Optional<ClientDataDto> clientData = Optional.of(constructClientDataDto());
        CollectionModel<ClientDataDto> collectionModel = CollectionModel.of(Collections.singletonList(clientData.get()));
        collectionModel.add(link);

        //Alternatively if entity needed
//        RepresentationModel<?> representationModel = RepresentationModel.of(clientData.get());
//        representationModel.add(link);

        return ResponseEntity.ok(collectionModel);
    }

    private ClientDataDto constructClientDataDto() {
        return ClientDataDto.builder()
                .partnerId("partnerId")
                .partnerFullName("partnerFullName")
                .language("language")
                .build();
    }
}
