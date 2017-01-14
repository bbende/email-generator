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

    static final String[] DOMAINS = { "@gmail.com", "@yahoo.com", "@hotmail.com" };

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
        final List<String> emails = new ArrayList<>();
        for (String domain : DOMAINS) {
            emails.addAll(generateEmails(emailInfo, domain));
        }
        return emails;
    }

    private List<String> generateEmails(EmailInfo emailInfo, String domain) {
        final String firstName = emailInfo.getFirstName() == null ? "" : emailInfo.getFirstName().trim();
        final String firstInitial = firstName.length() > 0 ? firstName.substring(0,1) : "";
        final String lastName = emailInfo.getFirstName() == null ? "" : emailInfo.getLastName().trim();
        final String number = emailInfo.getNumber() == null ? "" : emailInfo.getNumber().trim();

        final List<String> emails = new ArrayList<>();
        emails.add(firstName + lastName + domain);
        emails.add(lastName + firstName + domain);
        emails.add(firstName + "." + lastName + domain);
        emails.add(lastName + "." + firstName + domain);

        if (!firstInitial.isEmpty()) {
            emails.add(firstInitial + lastName + domain);
            emails.add(firstInitial + "." + lastName + domain);
        }

        // with number
        if (!number.isEmpty()) {
            emails.add(firstName + lastName + number + domain);
            emails.add(lastName + firstName + number + domain);
            emails.add(firstName + "." + lastName + number + domain);
            emails.add(lastName + "." + firstName + number + domain);

            if (!firstInitial.isEmpty()) {
                emails.add(firstInitial + lastName + number + domain);
                emails.add(firstInitial + "." + lastName + number + domain);
            }
        }

        return emails;
    }

}
