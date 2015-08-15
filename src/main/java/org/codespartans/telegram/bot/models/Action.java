package org.codespartans.telegram.bot.models;

/**
 * Type of action to broadcast.
 * Choose one, depending on what the user is about to receive:
 * <i>typing</i> for <a href="https://core.telegram.org/bots/api#sendmessage">text messages</a>,
 * <i>upload_photo</i> for <a href="https://core.telegram.org/bots/api#sendphoto">photos</a>,
 * <i>record_video</i> or <i>upload_video</i> for <a href="https://core.telegram.org/bots/api#sendvideo">videos</a>,
 * <i>record_audio</i> or <i>upload_audio</i> for <a href="https://core.telegram.org/bots/api#sendaudio">audio files</a>,
 * <i>upload_document</i> for <a href="https://core.telegram.org/bots/api#senddocument">general files</a>,
 * <i>find_location</i> for <a href="https://core.telegram.org/bots/api#sendlocation">location data</a>.
 */
public enum Action {
    TYPING,
    UPLOAD_PHOTO,
    RECORD_VIDEO,
    RECORD_AUDIO,
    UPLOAD_DOCUMENT,
    FIND_LOCATION
}
