# Tyroo Custom Event Sample - AdMob Mediation(Android)

A sample project demonstrating how Tyroo ad network can plug into AdMob Mediation. The project contains a “Tyroo Vid.AI” SDK library as gradle dependency, as well as a sample custom event implementation for AdMob Mediation capable of loading interstitials and native ads. A test application is also included, and uses the Google Mobile Ads SDK to call into custom event to test their implementations. It can be used during development to test new custom events, once ad units have been set up.

You do not need to write custom events for interstitial or native ads beacuse in the above sample code we have already written custom events for your convenience. You can either copy customevent module from above sample code or copy the particular class from the given sample code.

## For more Interstitial and Native ad guidelines you can visit AdMob official documentation:

[Interstitial Custom Event](https://developers.google.com/admob/android/custom-events#interstitial_custom_event)

[Native Custom Event](https://developers.google.com/admob/android/native-custom-events)

The custom event must be defined in the [AdMob interface](https://apps.admob.com/)

[How to add a Custom Event in AdMob dashboard](https://support.google.com/admob/answer/3083407)
