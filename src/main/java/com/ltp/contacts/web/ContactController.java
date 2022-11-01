package com.ltp.contacts.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ltp.contacts.exception.ErrorResponse;
import com.ltp.contacts.pojo.Contact;
import com.ltp.contacts.service.ContactService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Contact Controller", description = "Create, update and delete contacts.")
public class ContactController {
    
    @Autowired
    private ContactService contactService;


    @ApiResponse(responseCode = "200", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Contact.class))))
    @Operation(summary = "Retrieves contacts.", description = "Provides a list of all contacts.")
    @GetMapping("/contact/all")
    public ResponseEntity<List<Contact>> getContacts() {
        List<Contact> contacts = contactService.getContacts();
        return new ResponseEntity<>(contacts, HttpStatus.OK); 
    }


    @Operation(summary = "Get contact by Id.", description = "Returns a contact based on an ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Contact doesn't exist", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "200", description = "Successful retrieval of contact", content = @Content(schema = @Schema(implementation = Contact.class))),
    })
    @GetMapping("/contact/{id}")
    public ResponseEntity<Contact> getContact(@PathVariable String id) {
            Contact contact = contactService.getContactById(id);
            return new ResponseEntity<Contact>(contact, HttpStatus.OK); 
    }


    @Operation(summary = "Create Contact.", description = "Creates a contact from the provided payload.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successful creation of contact"),
            @ApiResponse(responseCode = "400", description = "Bad request: unsuccessful submission", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/contact")
    public ResponseEntity<HttpStatus> createContact(@RequestBody Contact contact) {
        contactService.saveContact(contact);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @Operation(summary = "Update Contact.", description = "Edits a contact based on an ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Contact doesn't exist", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "200", description = "Contact successfully updated", content = @Content(schema = @Schema(implementation = Contact.class))),
    })
    @PutMapping("/contact/{id}")
    public ResponseEntity<Contact> updateContact(@PathVariable String id, @RequestBody Contact contact) {
            contactService.updateContact(id, contact);
            return new ResponseEntity<Contact>(contactService.getContactById(id), HttpStatus.OK);
        
    }
    

    @Operation(summary = "Delete Contact.", description = "Deletes a contact based on an ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Contact doesn't exist", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "200", description = "Contact successfully deleted")
    })
    @DeleteMapping("/contact/{id}")
    public ResponseEntity<HttpStatus> deleteContact(@PathVariable String id) {                                   
            contactService.deleteContact(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); 
    }
}
