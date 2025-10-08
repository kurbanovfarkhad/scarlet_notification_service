package scarlett.notification.org.integration.serialization.avro;

import lombok.RequiredArgsConstructor;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.io.Decoder;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.io.Encoder;
import org.apache.avro.io.EncoderFactory;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificDatumWriter;
import org.springframework.stereotype.Component;
import scarlett.notification.org.common.model.QueuePayload;
import scarlett.notification.org.common.model.avro.AuroQueuePayload;
import scarlett.notification.org.integration.serialization.SchemaSerializer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@RequiredArgsConstructor
@Component("AVRO")
public class AvroSchemaSerializerImpl implements SchemaSerializer {
    private final AvroBaseSchemaMapper mapper;

    @Override
    public byte[] serialize(QueuePayload payload) {
        try {
            AuroQueuePayload map = mapper.map(payload);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            DatumWriter<AuroQueuePayload> writer = new SpecificDatumWriter<>(AuroQueuePayload.class);
            Encoder encoder = EncoderFactory.get().binaryEncoder(out, null);

            writer.write(map, encoder);
            encoder.flush();

            return out.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public QueuePayload deserialize(byte[] payload) {
        try {
            DatumReader<AuroQueuePayload> datumReader = new SpecificDatumReader<>(AuroQueuePayload.class);
            Decoder decoder = DecoderFactory.get().binaryDecoder(new ByteArrayInputStream(payload), null);
            AuroQueuePayload read = datumReader.read(null, decoder);

            return mapper.map(read);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
