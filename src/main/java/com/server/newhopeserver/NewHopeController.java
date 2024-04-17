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
import io.swagger.v3.oas.annotations.media.ExampleObject;
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

        @Operation(summary = "Obtiene los parametros pa y hint", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "JSON representando el polinomio y la semilla para el parametro comun", content = @Content(examples = {
                        @ExampleObject(name = "n = 32", value = "{\"polynom\":{\"coef\":[9800,10875,7781,4466,6913,3446,10216,4464,4858,6370,9359,4999,8963,4294,11390,8747,965,1141,1942,7379,10095,6491,4894,11197,1632,9156,3632,9989,4327,3407,7285,5508]},\"seed\":[-71,76,-11,-6,-58,49,18,110,123,-74,46,120,57,55,-21,-39,-76,-98,-72,-20,45,102,42,-13,105,-105,42,127,-95,22,95,105]}"),
                        @ExampleObject(name = "n = 128", value = "{\"polynom\":{\"coef\":[5217,6167,2552,4562,8101,5866,1349,10098,4882,8493,2529,9991,8658,4917,1490,3519,7194,9946,10320,10877,4850,11841,967,10762,4273,10731,9230,9708,1568,9609,2946,2189,11946,11061,2477,2276,12188,6346,2779,5382,5671,11443,6618,6294,4917,693,7853,7315,174,7962,2453,607,7204,2219,8704,3492,3923,10977,2206,5456,7126,7449,2944,5941,1355,8449,1714,3370,8504,6941,343,7163,2506,5884,10039,9332,109,2334,575,4257,9330,10134,6491,5129,5258,6322,1526,142,9969,10165,10586,7131,5529,1033,8489,5280,5889,1807,591,8090,3562,5777,6432,2385,8754,1880,4171,11627,2395,8948,156,4827,6042,9093,1171,7409,7686,2623,535,7106,8260,11457,12193,148,4510,4711,6308,3814]},\"seed\":[-67,-36,86,62,100,-104,71,126,-20,35,-14,-39,-65,-94,-104,114,121,16,-117,30,-107,19,-117,-87,63,28,44,88,-110,121,-27,-21]}"),
                        @ExampleObject(name = "n = 1024", value = "{\"polynom\":{\"coef\":[9943,657,6955,5380,6118,7243,3213,1017,7904,10355,7527,6475,2872,6831,4155,8723,1154,6828,12215,8307,5458,4509,2081,5646,8793,9821,10886,5517,5866,1447,10371,2097,4434,6340,9375,1007,2463,5600,7343,1714,1392,8012,7748,4688,2130,8731,6314,5887,10272,468,5023,9521,4410,104,7928,5931,6407,4279,3100,10586,6881,8470,4217,4310,7456,471,11324,2465,9560,9965,2227,1143,12204,2173,737,6865,1477,7463,4993,8107,4698,6362,10896,7976,3332,2416,5634,1038,3104,6606,418,1717,3666,9995,5309,1001,11817,4687,187,8538,2751,12130,9819,4376,4707,4774,2637,5434,9812,798,2127,2239,4737,11440,11867,9882,6239,11771,11114,2252,11986,3468,10650,12108,3005,10902,9003,3553,11445,8342,4499,1054,9938,4994,11420,6675,7100,7566,9384,6886,1802,4017,614,8487,4197,11986,921,8631,8591,4356,4388,5686,7500,7473,11091,12143,10520,8311,62,732,7747,1341,871,9013,8932,6545,7036,2577,4113,2329,9256,3140,5571,2772,5794,3357,9679,10890,258,11434,8561,356,7830,2857,8903,10058,8894,5501,5311,6084,10133,7000,1049,2695,8535,6973,6101,6919,9064,4854,3697,8484,3403,2811,8681,8236,2396,7028,10644,4347,1273,10887,995,5699,3379,6496,672,1616,11445,2384,6510,1499,9371,9328,11792,3394,10457,1732,9575,7790,6284,11844,7709,1780,5244,5,10824,12207,3391,8121,9673,167,1877,8218,9872,5986,5535,1854,3123,7552,12157,1968,11148,11057,1617,327,8546,10063,4710,9008,2040,3341,5654,11031,11852,9173,3042,1704,11802,3092,8904,3758,1223,10812,6614,4526,5065,5676,5226,1825,11953,6870,8934,1461,6193,11066,8292,3394,9311,5435,4798,8631,4755,685,8263,1252,5667,332,1092,8058,6244,11750,11111,2941,7003,8636,8843,506,5752,9740,8908,8399,5641,6747,4654,6269,6034,7266,5498,11302,4482,3963,9140,12064,9620,8195,3066,10729,1473,10331,4243,278,9398,10896,12002,11088,345,11254,11996,12199,6250,3271,1520,4206,8543,6343,8343,9725,565,2895,5482,8486,9216,3323,2853,2352,3739,3081,10591,1792,3450,4528,165,5170,3224,8674,11665,12178,11667,740,4445,5624,11650,7184,2424,7929,7472,9811,6988,872,8075,9702,6717,7640,6311,10478,11112,11232,5407,4164,11826,9791,1426,906,3850,5380,6289,5833,4887,5773,11042,3429,172,2076,210,4931,1079,9136,8167,11977,11187,5003,2827,3551,2676,10223,8736,9902,3106,4162,6723,10392,4071,6072,3863,1569,4940,1859,10316,2722,12221,3262,8197,5281,11164,3175,6473,3171,4101,6106,4455,2649,6790,8017,3624,11665,5854,11731,8038,4599,5172,8886,7326,8820,9916,9444,257,3475,22,2221,8684,5876,4648,9551,8262,6483,8492,1641,8387,5103,107,1920,8261,6059,10122,3452,11772,591,4846,4275,11048,3208,3145,8567,1716,3438,9603,11243,8339,1597,2500,5741,8018,11495,2554,2478,6086,1024,4846,3646,10088,10824,10429,10720,2929,3930,9243,7311,8943,1089,697,1225,8940,12018,8034,9930,2162,1723,1075,9518,874,4926,5070,11502,10202,6727,6483,12217,5889,11206,8230,4412,2303,3101,5910,5028,8958,2274,10790,2162,5274,3335,11596,9531,5117,6584,1939,2487,10186,10254,8015,7733,978,7516,10857,10065,6619,6868,7410,5406,3490,5430,3676,2541,1031,3720,12288,6327,11392,6471,5651,3134,3067,7529,12233,7413,3628,5705,11896,8386,8577,9680,9298,3824,1082,1263,2538,8200,9560,9485,8844,8829,5650,1811,978,11908,8941,5672,11929,10993,10072,11775,10323,6863,6221,12188,2475,3080,98,4424,5770,2494,6413,1016,3276,5376,1195,7672,7633,3503,3464,9113,7453,6233,4623,4574,5483,10208,464,5483,2753,6589,637,7889,9589,3593,10869,3060,11849,6644,723,11536,4073,6706,8639,1059,4676,8739,8480,3883,2860,7,2514,8636,3210,5719,9214,6746,5640,9833,11056,5231,6907,8586,4305,10075,1066,1412,934,3789,10461,4483,10710,2849,9892,584,10081,6486,977,1279,3356,1059,12146,4955,4585,6871,10609,6207,2649,9830,8869,1976,9495,6769,6190,7291,1519,996,10981,6874,8866,147,4978,1141,4031,11022,10264,9153,9078,4499,10236,335,963,9363,8268,11600,7815,523,5331,5985,11526,3570,8450,7441,5715,952,3455,7314,5991,5237,9073,12239,637,8846,9561,10466,1395,8388,7928,10830,6835,1948,8809,2004,9986,4024,8090,7113,79,1754,11123,9572,4309,10647,1370,1886,8668,2476,10314,7406,8141,3197,3922,2036,10518,10515,10047,2587,7243,2751,3,8108,3588,337,11699,5311,4921,4576,1653,8317,3083,10265,6298,8011,12232,9467,6521,3150,10481,5888,1245,197,568,12105,7077,856,1737,3973,4915,9222,12171,1970,6562,5173,3874,4395,9601,10073,1308,2331,10341,5733,9477,10433,5005,8014,196,687,7748,6155,11480,1258,4854,6910,11369,5629,8703,11375,4831,2432,758,10799,3856,8447,5965,4987,151,3255,2268,10538,733,2833,7590,4601,4109,2912,11305,5309,9806,7298,10628,1050,10616,8876,11920,9578,2567,7940,9924,898,7416,3265,6505,5817,1240,10211,4548,11972,8661,9897,10412,13,11545,8935,1516,9499,6843,6321,4993,4812,2481,10748,7451,3333,5598,5198,12002,7630,825,5774,5069,8789,2029,6017,6350,167,7669,12090,10262,1969,11844,11523,10171,5353,8100,8479,8349,11420,7043,10953,3300,2273,4470,5340,7602,1896,10946,7380,6861,3753,6527,3898,4927,3663,2752,9120,8644,822,8091,2344,64,8053,9618,1363,1002,11175,3974,917,1759,8340,8357,5062,10255,5839,6181,8700,5014,8608,4505,7978,3537,1683,2948,830,4174,12130,77,6284,10067,485,10671,2161,2279,8797,11669,1992,11646,3247,9326,8966,6508,10606,11616,3186,4412,6659,7017,8811,6634,7133,1967,9381,6437,6767,8383,486,10690,3497,8613,1944,2639,6622,1879,3533,6561,4105,9280,582,871,7196,4248,7985,1760,2342,1449,10241,5513,6322,4213,1290,1642,746,5054,4885,2983,8792,5267,4290,2514,8753,11212,9276,5770,5756]},\"seed\":[-18,117,93,-30,126,-10,-38,65,-50,-114,109,39,-66,-71,-6,-41,-105,-66,-120,118,-81,122,-74,90,-21,56,103,21,-58,-108,-79,-90]}"),
        })))

        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Listado de productos", content = @Content(schema = @Schema(implementation = jsonResponse.class))),
                        @ApiResponse(responseCode = "400", description = "Error en algun parametro de la mensaje", content = @Content(schema = @Schema(implementation = badParam.class))),
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

                if (rmessage.polynom().coef().length != n) {
                        errores.put("body", "El polinomio no tiene el numero correcto de coeficientes: "
                                        + rmessage.polynom().coef().length);
                }
                if (errores.size() != 0) {
                        throw new badParamException(errores);
                }
                byte[] seed = rmessage.seed();
                Polynomial pb = new Polynomial(rmessage.polynom().coef());

                nh = new NewHope(n, q);

                Polynomial sa = nh.generateBinoPol();
                Polynomial ea = nh.generateBinoPol();
                Polynomial ea1 = nh.generateBinoPol();
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
                Long[] temp = rmessage.getCoefsList().toArray(new Long[0]);

                if (temp.length != n) {
                        errores.put("body", "El polinomio no tiene el numero correcto de coeficientes: "
                                        + temp.length);
                }
                if (errores.size() != 0) {
                        throw new badParamException(errores);
                }
                nh = new NewHope(n, q);

                Polynomial sa = nh.generateBinoPol();
                Polynomial ea = nh.generateBinoPol();
                Polynomial ea1 = nh.generateBinoPol();
                long[] coef = new long[temp.length];
                for (int i = 0; i < temp.length; i++) {
                        coef[i] = (long) temp[i];
                }
                byte[] seed = new byte[32];
                Polynomial pb = new Polynomial();

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
