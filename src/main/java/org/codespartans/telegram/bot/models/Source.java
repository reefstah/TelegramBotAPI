package org.codespartans.telegram.bot.models;

import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;

import java.io.File;

/**
 * Created by ralph on 14/08/15.
 */
public class Source<T> {

    private Item<T> source;

    private Source(Item<T> source) {
        this.source = source;
    }

    public static Source<String> of(String file_id) {
        return new Source<>(new StringSource(file_id));
    }

    public static Source<File> of(File file) {
        return new Source<>(new FileSource(file));
    }

    public void setField(MultipartEntityBuilder entityBuilder, String field) {
        this.source.setField(entityBuilder, field);
    }

    private static abstract class Item<T> {

        private T value;

        public Item(T value) {
            this.value = value;
        }

        abstract void setField(MultipartEntityBuilder entityBuilder, String field);
    }

    private static class StringSource extends Item<String> {

        public StringSource(String value) {
            super(value);
        }

        @Override
        void setField(MultipartEntityBuilder entityBuilder, String field) {
            entityBuilder.addTextBody(field, super.value);
        }
    }

    private static class FileSource extends Item<File> {
        public FileSource(File value) {
            super(value);
        }

        @Override
        void setField(MultipartEntityBuilder entityBuilder, String field) {
            entityBuilder.addPart(field, new FileBody(super.value));
        }
    }
}
