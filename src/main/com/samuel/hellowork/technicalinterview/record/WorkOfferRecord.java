package main.com.samuel.hellowork.technicalinterview.record;

import org.bson.codecs.pojo.annotations.BsonId;

import java.util.Date;

public record WorkOfferRecord(
        @BsonId
        String reference,
        String description,
        Date date
) {}
