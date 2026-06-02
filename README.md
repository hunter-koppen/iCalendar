# iCalendar / ICS Module

An easy way to generate appointment (`.ics`) files to attach to an email.

## Features

- Generate create, update, and cancel `.ics` appointment files
- Add extra details to the appointment such as location, description, organizer, and attendees
- Choose how times are interpreted: as fixed moments (UTC) or as wall-clock times in a specific time zone
- Optionally set a time zone

## Dependencies

- None

The Email Connector module is mentioned in the [Email headers](#email-headers) section as a convenient way to attach the file, but it is not required.

## Usage

Add the `SUB_IcalMessage_CreateFile` microflow wherever you want to generate an `.ics` file. It needs an `IcalMessage` object as input.

Mandatory fields are the start time, end time, subject, and status (the status maps to the iCal `METHOD` — see [Email headers](#email-headers)). The remaining fields are optional, but it's recommended to populate as many as you can for a complete invite.

The `AttendeeList` parameter requires a list of `Attendee` objects. Do not rename the `Attendee` entity or its attributes — the action looks them up by name.

To test the generated files, add the `SNIP_ICalendar` snippet to a page.

## Localized dates

The `Localized` boolean controls how the start and end values are interpreted:

- **`true`** — the dates are treated as real moments in time (a _localized_ Mendix DateTime attribute, or a date you built to represent an actual instant). They are written in UTC, and every calendar client converts them to the recipient's own time zone. The `TimeZone` parameter is ignored in this mode.
- **`false` or empty** — the dates are treated as wall-clock labels (a _non-localized_ attribute). The exact time you set is written as-is under the chosen `TimeZone`, so 14:00 stays 14:00 in that zone regardless of where the recipient is.

Rule of thumb: use `true` for "the meeting is at this precise instant," and `false` for "the meeting is at 14:00 local time, whatever the recipient's zone."

## Time zones

The `TimeZone` parameter only applies when `Localized` is `false` or empty. In localized mode the times are always written in UTC, so this field is ignored.

Leave it empty to fall back to the server's time zone. To set one explicitly, the easiest option is the `TimeZone` entity that already exists in the System module of Studio Pro — put its `Code` attribute into the `TimeZone` field. You can also set it manually if you only ever use one zone, for example `Europe/London`.

## Email headers

To send an `.ics` file correctly via email, you need to set the right attachment content type. If you use the Email Connector module, attach the file with its Attachment mechanism and set the `attachmentContentType` attribute on the `Attachment` entity. The value should look like this:

```
text/calendar; charset=utf-8; method=REQUEST; name=event.ics
```

Make sure the `method` matches the iCal status you used (`REQUEST` for a create or update, `CANCEL` for a cancellation, and so on) — it must be the same value as the `METHOD` written into the file.

## Sending an update for an appointment

Updates to an existing appointment must use the **same UID** so that calendar clients recognise it as the same event. You should also increase the **sequence number** on each update: the first mail carries an `.ics` with sequence `0`, the next one `1`, and so on (for example, when you later cancel an appointment you already sent).

Because of this, it's wise to commit the `IcalMessage` object after sending it the first time, then retrieve that same object when sending an update. `SUB_IcalMessage_CreateFile` increments the sequence number for you.

## Issues, suggestions, and feature requests

Please feel free to raise issues, share suggestions, or request new features on the GitHub repository:
[iCalendar GitHub Issues](https://github.com/hunter-koppen/iCalendar/issues)
