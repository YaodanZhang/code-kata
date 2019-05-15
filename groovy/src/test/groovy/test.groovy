byte[] string = new File('/Users/twer/Library/Containers/com.secureauth.SecureAuth-OTP/Data/Library/Caches/com.secureauth.SecureAuth-OTP/Cache.db-shm').bytes
File output = new File('/tmp/test.hex')

final Writable printableHex = string.encodeHex()

String hex = '78F81B58C365702D2EA14B90C972C840561636B932E99CB915AC4CB9990FCF14F1149373696A84A838640BB5C8286B055D30C010B906B0B86C1352AB746911061322E75102EB482AB755C8401003C5C21BAABD8F63C74E23865997521D3AB12C482C404CB66E97129778B709B25CB2D9176448'

println hex

Set<String> set = new HashSet<>()

for (int i = 40; i <= hex.length(); i++) {
    set.add("${hex.substring(i - 40, i)}${System.lineSeparator()}".toString())
}

set.forEach({ it ->
    output << "${it}${System.lineSeparator()}"
})