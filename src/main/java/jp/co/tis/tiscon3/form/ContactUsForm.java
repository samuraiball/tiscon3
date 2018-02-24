package jp.co.tis.tiscon3.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ContactUsForm extends FormBase {

    private static final long serialVersionUID = 1L;

    @NotBlank
    String mail;

    @NotBlank
    String title;

    @NotBlank
    String genre;

    @NotBlank
    String content;
}
