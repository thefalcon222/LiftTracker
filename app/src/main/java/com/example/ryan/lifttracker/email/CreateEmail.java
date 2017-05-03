package com.example.ryan.lifttracker.email;

import android.content.Context;
import android.content.Intent;
import static android.R.id.message;

/**
 * Created by Andrew Walters on 5/2/2017.
 */

public class CreateEmail
{

    Intent chooser;

    public CreateEmail(String to, String subject, String message)
    {
        Intent email = new Intent(Intent.ACTION_SEND);
        email.putExtra(Intent.EXTRA_EMAIL, new String[]{to});
        email.putExtra(Intent.EXTRA_SUBJECT, subject);
        email.putExtra(Intent.EXTRA_TEXT, message);

        email.setType("message/rfc822");
        chooser = Intent.createChooser(email, "Send mail...");

        //Then we need to actually start the activity
    }

    public Intent getChooser()
    {
        return chooser;
    }
}

