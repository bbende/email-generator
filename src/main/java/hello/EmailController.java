package hello;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class EmailController {

    @GetMapping("/email")
    public String emailForm(Model model) {
        model.addAttribute("emailInfo", new EmailInfo());
        return "email";
    }

    @PostMapping("/email")
    public String emailSubmit(@ModelAttribute EmailInfo emailInfo) {
        List<String> emails = generateEmails(emailInfo);
        emailInfo.setEmails(emails);
        return "result";
    }

    private List<String> generateEmails(EmailInfo emailInfo) {
        final String firstName = emailInfo.getFirstName() == null ? "" : emailInfo.getFirstName().trim().toLowerCase();
        final String firstInitial = firstName.length() > 0 ? firstName.substring(0,1) : "";
        final String lastName = emailInfo.getFirstName() == null ? "" : emailInfo.getLastName().trim().toLowerCase();
        final String number = emailInfo.getNumber() == null ? "" : emailInfo.getNumber().trim();

        String domain = emailInfo.getDomain() == null ? "" : emailInfo.getDomain().trim().toLowerCase();
        if (!domain.startsWith("@")) {
            domain = "@" + domain;
        }

        final List<String> emails = new ArrayList<>();
        emails.add(firstName + "." + lastName + domain);
        emails.add(firstName + "_" + lastName + domain);
        emails.add(firstName + lastName + domain);

        if (!firstInitial.isEmpty()) {
            emails.add(firstInitial + lastName + domain);
            emails.add(firstInitial + "." + lastName + domain);
            emails.add(firstInitial + "_" + lastName + domain);
        }

        // with number
        if (!number.isEmpty()) {
            emails.add(firstName + "." + lastName + number + domain);
            emails.add(firstName + "_" + lastName + number +domain);
            emails.add(firstName + lastName + number +domain);

            if (!firstInitial.isEmpty()) {
                emails.add(firstInitial + lastName + number +domain);
                emails.add(firstInitial + "." + lastName + number +domain);
                emails.add(firstInitial + "_" + lastName + number +domain);
            }
        }

        return emails;
    }

}
