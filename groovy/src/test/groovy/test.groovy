byte[] string = new File('/Users/twer/Library/Containers/com.secureauth.SecureAuth-OTP/Data/Library/Caches/com.secureauth.SecureAuth-OTP/Cache.db-shm').bytes
File output = new File('/tmp/test.hex')

final Writable printableHex = string.encodeHex()

String hex = printableHex.toString().toUpperCase()

println hex

Set<String> set = new HashSet<>()

for (int i = 40; i <= hex.length(); i++) {
    set.add("${hex.substring(i - 40, i)}".toString())
}

set.forEach({ it ->
    output << "${it}${System.lineSeparator()}"
})