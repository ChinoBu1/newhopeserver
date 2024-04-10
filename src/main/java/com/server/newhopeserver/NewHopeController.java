package com.server.newhopeserver;

import java.util.HashMap;

import java.util.Set;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.protobuf.InvalidProtocolBufferException;
import com.server.newhopeserver.proto.protoRequest;
import com.server.newhopeserver.proto.protoResponse;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;

@RestController
public class NewHopeController {
        NewHope nh;

        static final Set<Integer> primos = Set.of(3257, 3259, 3271, 3299, 3301, 3307, 3313, 3319, 3323, 3329, 3331,
                        3343, 3347, 3359, 3361, 3371, 3373, 3389, 3391, 3407, 3413, 3433, 3449, 3457, 3461,
                        3463, 3467, 3469, 3491, 3499, 3511, 3517, 3527, 3529, 3533, 3539, 3541, 3547, 3557,
                        3559, 3571, 3581, 3583, 3593, 3607, 3613, 3617, 3623, 3631, 3637, 3643, 3659, 3671,
                        3673, 3677, 3691, 3697, 3701, 3709, 3719, 3727, 3733, 3739, 3761, 3767, 3769, 3779,
                        3793, 3797, 3803, 3821, 3823, 3833, 3847, 3851, 3853, 3863, 3877, 3881, 3889, 3907,
                        3911, 3917, 3919, 3923, 3929, 3931, 3943, 3947, 3967, 3989, 4001, 4003, 4007, 4013,
                        4019, 4021, 4027, 4049, 4051, 4057, 4073, 4079, 4091, 4093, 4099, 4111, 4127, 4129,
                        4133, 4139, 4153, 4157, 4159, 4177, 4201, 4211, 4217, 4219, 4229, 4231, 4241, 4243,
                        4253, 4259, 4261, 4271, 4273, 4283, 4289, 4297, 4327, 4337, 4339, 4349, 4357, 4363,
                        4373, 4391, 4397, 4409, 4421, 4423, 4441, 4447, 4451, 4457, 4463, 4481, 4483, 4493,
                        4507, 4513, 4517, 4519, 4523, 4547, 4549, 4561, 4567, 4583, 4591, 4597, 4603, 4621,
                        4637, 4639, 4643, 4649, 4651, 4657, 4663, 4673, 4679, 4691, 4703, 4721, 4723, 4729,
                        4733, 4751, 4759, 4783, 4787, 4789, 4793, 4799, 4801, 4813, 4817, 4831, 4861, 4871,
                        4877, 4889, 4903, 4909, 4919, 4931, 4933, 4937, 4943, 4951, 4957, 4967, 4969, 4973,
                        4987, 4993, 4999, 5003, 5009, 5011, 5021, 5023, 5039, 5051, 5059, 5077, 5081, 5087,
                        5099, 5101, 5107, 5113, 5119, 5147, 5153, 5167, 5171, 5179, 5189, 5197, 5209, 5227,
                        5231, 5233, 5237, 5261, 5273, 5279, 5281, 5297, 5303, 5309, 5323, 5333, 5347, 5351,
                        5381, 5387, 5393, 5399, 5407, 5413, 5417, 5419, 5431, 5437, 5441, 5443, 5449, 5471,
                        5477, 5479, 5483, 5501, 5503, 5507, 5519, 5521, 5527, 5531, 5557, 5563, 5569, 5573,
                        5581, 5591, 5623, 5639, 5641, 5647, 5651, 5653, 5657, 5659, 5669, 5683, 5689, 5693,
                        5701, 5711, 5717, 5737, 5741, 5743, 5749, 5779, 5783, 5791, 5801, 5807, 5813, 5821,
                        5827, 5839, 5843, 5849, 5851, 5857, 5861, 5867, 5869, 5879, 5881, 5897, 5903, 5923,
                        5927, 5939, 5953, 5981, 5987, 6007, 6011, 6029, 6037, 6043, 6047, 6053, 6067, 6073,
                        6079, 6089, 6091, 6101, 6113, 6121, 6131, 6133, 6143, 6151, 6163, 6173, 6197, 6199,
                        6203, 6211, 6217, 6221, 6229, 6247, 6257, 6263, 6269, 6271, 6277, 6287, 6299, 6301,
                        6311, 6317, 6323, 6329, 6337, 6343, 6353, 6359, 6361, 6367, 6373, 6379, 6389, 6397,
                        6421, 6427, 6449, 6451, 6469, 6473, 6481, 6491, 6521, 6529, 6547, 6551, 6553, 6563,
                        6569, 6571, 6577, 6581, 6599, 6607, 6619, 6637, 6653, 6659, 6661, 6673, 6679, 6689,
                        6691, 6701, 6703, 6709, 6719, 6733, 6737, 6761, 6763, 6779, 6781, 6791, 6793, 6803,
                        6823, 6827, 6829, 6833, 6841, 6857, 6863, 6869, 6871, 6883, 6899, 6907, 6911, 6917,
                        6947, 6949, 6959, 6961, 6967, 6971, 6977, 6983, 6991, 6997, 7001, 7013, 7019, 7027,
                        7039, 7043, 7057, 7069, 7079, 7103, 7109, 7121, 7127, 7129, 7151, 7159, 7177, 7187,
                        7193, 7207, 7211, 7213, 7219, 7229, 7237, 7243, 7247, 7253, 7283, 7297, 7307, 7309,
                        7321, 7331, 7333, 7349, 7351, 7369, 7393, 7411, 7417, 7433, 7451, 7457, 7459, 7477,
                        7481, 7487, 7489, 7499, 7507, 7517, 7523, 7529, 7537, 7541, 7547, 7549, 7559, 7561,
                        7573, 7577, 7583, 7589, 7591, 7603, 7607, 7621, 7639, 7643, 7649, 7669, 7673, 7681,
                        7687, 7691, 7699, 7703, 7717, 7723, 7727, 7741, 7753, 7757, 7759, 7789, 7793, 7817,
                        7823, 7829, 7841, 7853, 7867, 7873, 7877, 7879, 7883, 7901, 7907, 7919, 7927, 7933,
                        7937, 7949, 7951, 7963, 7993, 8009, 8011, 8017, 8039, 8053, 8059, 8069, 8081, 8087,
                        8089, 8093, 8101, 8111, 8117, 8123, 8147, 8161, 8167, 8171, 8179, 8191, 8209, 8219,
                        8221, 8231, 8233, 8237, 8243, 8263, 8269, 8273, 8287, 8291, 8293, 8297, 8311, 8317,
                        8329, 8353, 8363, 8369, 8377, 8387, 8389, 8419, 8423, 8429, 8431, 8443, 8447, 8461,
                        8467, 8501, 8513, 8521, 8527, 8537, 8539, 8543, 8563, 8573, 8581, 8597, 8599, 8609,
                        8623, 8627, 8629, 8641, 8647, 8663, 8669, 8677, 8681, 8689, 8693, 8699, 8707, 8713,
                        8719, 8731, 8737, 8741, 8747, 8753, 8761, 8779, 8783, 8803, 8807, 8819, 8821, 8831,
                        8837, 8839, 8849, 8861, 8863, 8867, 8887, 8893, 8923, 8929, 8933, 8941, 8951, 8963,
                        8969, 8971, 8999, 9001, 9007, 9011, 9013, 9029, 9041, 9043, 9049, 9059, 9067, 9091,
                        9103, 9109, 9127, 9133, 9137, 9151, 9157, 9161, 9173, 9181, 9187, 9199, 9203, 9209,
                        9221, 9227, 9239, 9241, 9257, 9277, 9281, 9283, 9293, 9311, 9319, 9323, 9337, 9341,
                        9343, 9349, 9371, 9377, 9391, 9397, 9403, 9413, 9419, 9421, 9431, 9433, 9437, 9439,
                        9461, 9463, 9467, 9473, 9479, 9491, 9497, 9511, 9521, 9533, 9539, 9547, 9551, 9587,
                        9601, 9613, 9619, 9623, 9629, 9631, 9643, 9649, 9661, 9677, 9679, 9689, 9697, 9719,
                        9721, 9733, 9739, 9743, 9749, 9767, 9769, 9781, 9787, 9791, 9803, 9811, 9817, 9829,
                        9833, 9839, 9851, 9857, 9859, 9871, 9883, 9887, 9901, 9907, 9923, 9929, 9931, 9941,
                        9949, 9967, 9973, 10007, 10009, 10037, 10039, 10061, 10067, 10069, 10079, 10091, 10093,
                        10099, 10103, 10111, 10133, 10139, 10141, 10151, 10159, 10163, 10169, 10177, 10181,
                        10193, 10211, 10223, 10243, 10247, 10253, 10259, 10267, 10271, 10273, 10289, 10301,
                        10303, 10313, 10321, 10331, 10333, 10337, 10343, 10357, 10369, 10391, 10399, 10427,
                        10429, 10433, 10453, 10457, 10459, 10463, 10477, 10487, 10499, 10501, 10513, 10529,
                        10531, 10559, 10567, 10589, 10597, 10601, 10607, 10613, 10627, 10631, 10639, 10651,
                        10657, 10663, 10667, 10687, 10691, 10709, 10711, 10723, 10729, 10733, 10739, 10753,
                        10771, 10781, 10789, 10799, 10831, 10837, 10847, 10853, 10859, 10861, 10867, 10883,
                        10889, 10891, 10903, 10909, 10937, 10939, 10949, 10957, 10973, 10979, 10987, 10993,
                        11003, 11027, 11047, 11057, 11059, 11069, 11071, 11083, 11087, 11093, 11113, 11117,
                        11119, 11131, 11149, 11159, 11161, 11171, 11173, 11177, 11197, 11213, 11239, 11243,
                        11251, 11257, 11261, 11273, 11279, 11287, 11299, 11311, 11317, 11321, 11329, 11351,
                        11353, 11369, 11383, 11393, 11399, 11411, 11423, 11437, 11443, 11447, 11467, 11471,
                        11483, 11489, 11491, 11497, 11503, 11519, 11527, 11549, 11551, 11579, 11587, 11593,
                        11597, 11617, 11621, 11633, 11657, 11677, 11681, 11689, 11699, 11701, 11717, 11719,
                        11731, 11743, 11777, 11779, 11783, 11789, 11801, 11807, 11813, 11821, 11827, 11831,
                        11833, 11839, 11863, 11867, 11887, 11897, 11903, 11909, 11923, 11927, 11933, 11939,
                        11941, 11953, 11959, 11969, 11971, 11981, 11987, 12007, 12011, 12037, 12041, 12043,
                        12049, 12071, 12073, 12097, 12101, 12107, 12109, 12113, 12119, 12143, 12149, 12157,
                        12161, 12163, 12197, 12203, 12211, 12227, 12239, 12241, 12251, 12253, 12263, 12269,
                        12277, 12281, 12289);

        @Operation(summary = "Obtiene los parametros pa y hint")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Listado de productos", content = @Content(schema = @Schema(implementation = jsonResponse.class))),
                        @ApiResponse(responseCode = "400", description = "Error en algun parametro de la query", content = @Content(schema = @Schema(implementation = badParam.class))),
        })
        @PostMapping(path = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<jsonResponse> getJson(HttpServletRequest request,
                        @RequestParam(value = "n", defaultValue = "1024") int n,
                        @RequestParam(value = "q", defaultValue = "12289") int q, @RequestBody jsonRequest rmessage)
                        throws InvalidProtocolBufferException {

                final HttpHeaders httpHeaders = new HttpHeaders();
                HashMap<String, String> errores = new HashMap<String, String>();

                if (n < 32 || n > 1024) {
                        errores.put("n", "Debe ser un numero entre 32 y 1024: " + n);
                }
                if (!primos.contains(q)) {
                        errores.put("q", " Debe ser un numero primo entre 3257 y 12289: " + q);
                }

                if (errores.size() != 0) {
                        throw new badParamException(errores);
                }
                nh = new NewHope(n, q);

                Polynomial sa = nh.generateBinoPol();
                Polynomial ea = nh.generateBinoPol();
                Polynomial ea1 = nh.generateBinoPol();

                byte[] seed = rmessage.seed();
                Polynomial pb = new Polynomial(rmessage.polynom().coef());

                Polynomial m = nh.parseSeed(seed);
                Polynomial pa = Polynomial.PolyModInt(
                                Polynomial.PolyModF(
                                                Polynomial.PolySum(
                                                                Polynomial.PolyMult(m, sa),
                                                                ea),
                                                nh.getF()),
                                nh.getQ());
                Polynomial Ka = Polynomial.PolyModInt(
                                Polynomial.PolyModF(
                                                Polynomial.PolySum(
                                                                Polynomial.PolyMult(pb, sa),
                                                                ea1),
                                                nh.getF()),
                                nh.getQ());
                int[][] hint = nh.hint(Ka);

                int[] SK = nh.Rec(Ka, hint);
                byte[] K = nh.toByte(SK);
                for (byte b : K) {
                        System.out.print(b + " ");
                }
                System.out.println();

                return new ResponseEntity<jsonResponse>(new jsonResponse(new polynom(pa.GetCoef()), hint), httpHeaders,
                                HttpStatus.OK);
        }

        @Hidden
        @PostMapping(path = "/", consumes = "application/x-protobuf", produces = "application/x-protobuf")
        public ResponseEntity<protoResponse> getProtobuf(HttpServletRequest request,
                        @RequestParam(value = "n", defaultValue = "1024") int n,
                        @RequestParam(value = "q", defaultValue = "12289") int q, @RequestBody protoRequest rmessage)
                        throws InvalidProtocolBufferException {

                final HttpHeaders httpHeaders = new HttpHeaders();
                HashMap<String, String> errores = new HashMap<String, String>();
                if (n < 32 || n > 1024) {
                        errores.put("n", "Debe ser un numero entre 32 y 1024: " + n);
                }
                if (!primos.contains(q)) {
                        errores.put("q", " Debe ser un numero primo entre 3257 y 12289: " + q);
                }

                if (errores.size() != 0) {
                        throw new badParamException(errores);
                }
                nh = new NewHope(n, q);

                Polynomial sa = nh.generateBinoPol();
                Polynomial ea = nh.generateBinoPol();
                Polynomial ea1 = nh.generateBinoPol();

                byte[] seed = new byte[32];
                Polynomial pb = new Polynomial();

                Long[] temp = rmessage.getCoefsList().toArray(new Long[0]);
                long[] coef = new long[temp.length];
                for (int i = 0; i < temp.length; i++) {
                        coef[i] = (long) temp[i];
                }
                pb = new Polynomial(coef);
                seed = rmessage.getSeed().toByteArray();

                Polynomial m = nh.parseSeed(seed);
                Polynomial pa = Polynomial.PolyModInt(
                                Polynomial.PolyModF(
                                                Polynomial.PolySum(
                                                                Polynomial.PolyMult(m, sa),
                                                                ea),
                                                nh.getF()),
                                nh.getQ());
                Polynomial Ka = Polynomial.PolyModInt(
                                Polynomial.PolyModF(
                                                Polynomial.PolySum(
                                                                Polynomial.PolyMult(pb, sa),
                                                                ea1),
                                                nh.getF()),
                                nh.getQ());
                int[][] hint = nh.hint(Ka);
                protoResponse.Builder b = protoResponse.newBuilder();
                for (long c : pa.GetCoef()) {
                        b.addCoefs(c);
                }
                for (int[] i : hint) {
                        for (int j : i) {
                                b.addHints(j);

                        }
                }
                protoResponse msg = b.build();

                int[] SK = nh.Rec(Ka, hint);
                byte[] K = nh.toByte(SK);
                for (byte bytes : K) {
                        System.out.print(bytes + " ");
                }
                System.out.println();

                return new ResponseEntity<protoResponse>(msg, httpHeaders, HttpStatus.OK);
        }

        @ExceptionHandler(badParamException.class)
        public ResponseEntity<badParam> handleBadRequest(badParamException e) {
                System.out.println();
                return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_PROBLEM_JSON)
                                .body(new badParam(e.badParam));

        }

        // @PostMapping(path = "/", consumes = "application/octet-stream", produces =
        // "application/octet-stream")
        // public ResponseEntity<byte[]> getHint(@RequestParam(value = "n", defaultValue
        // = "1024") int n,
        // @RequestParam(value = "q", defaultValue = "12289") int q, @RequestBody byte[]
        // rmessage)
        // throws InvalidProtocolBufferException {

        // final HttpHeaders httpHeaders = new HttpHeaders();

        // if (n < 32 || !primos.contains(q)) {
        // return new ResponseEntity<byte[]>(
        // n < 32 ? "El parametro n tiene que ser mayor que 32".getBytes()
        // : "q debe ser un primo entre 3257 y 12289".getBytes(),
        // httpHeaders,
        // HttpStatus.BAD_REQUEST);
        // }
        // nh = new NewHope(n, q);

        // Polynomial sa = nh.generateBinoPol();
        // Polynomial ea = nh.generateBinoPol();
        // Polynomial ea1 = nh.generateBinoPol();

        // byte[] message = new byte[0];
        // byte[] seed = new byte[32];
        // Polynomial pb = new Polynomial();

        // byte[] pbByte = new byte[rmessage.length - 32];
        // System.arraycopy(rmessage, rmessage.length - 32, seed, 0, 32);
        // System.arraycopy(rmessage, 0, pbByte, 0, rmessage.length - 32);
        // pb = nh.fromByteArray(pbByte);
        // Polynomial m = nh.parseSeed(seed);
        // Polynomial pa = Polynomial.PolyModInt(
        // Polynomial.PolyModF(
        // Polynomial.PolySum(
        // Polynomial.PolyMult(m, sa),
        // ea),
        // nh.getF()),
        // nh.getQ());
        // Polynomial Ka = Polynomial.PolyModInt(
        // Polynomial.PolyModF(
        // Polynomial.PolySum(
        // Polynomial.PolyMult(pb, sa),
        // ea1),
        // nh.getF()),
        // nh.getQ());
        // int[][] hint = nh.hint(Ka);

        // httpHeaders.setContentType(new MediaType("application", "octet-stream"));
        // byte[] hintByte = new byte[hint.length * 4 * 4];
        // for (int i = 0; i < hint.length; i++) {
        // for (int j = 0; j < hint[i].length; j++) {
        // hintByte[4 * (4 * i + j)] = (byte) (hint[i][j]);
        // hintByte[4 * (4 * i + j) + 1] = (byte) (hint[i][j] >> 8);
        // hintByte[4 * (4 * i + j) + 2] = (byte) (hint[i][j] >> 16);
        // hintByte[4 * (4 * i + j) + 3] = (byte) (hint[i][j] >> 24);
        // }
        // }

        // byte[] paByte = nh.toByteArray(pa);
        // message = new byte[paByte.length + hintByte.length];
        // System.arraycopy(paByte, 0, message, 0, paByte.length);
        // System.arraycopy(hintByte, 0, message, paByte.length, hintByte.length);

        // int[] SK = nh.Rec(Ka, hint);
        // byte[] K = nh.toByte(SK);
        // for (byte b : K) {
        // System.out.print(b + " ");
        // }
        // System.out.println();

        // return new ResponseEntity<byte[]>(message, httpHeaders, HttpStatus.OK);
        // }
}
