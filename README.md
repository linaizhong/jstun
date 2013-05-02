jstun
=====



Just a copy of JStun (http://jstun.javawi.de/) with my notes as I work out how to use it

<h5>Run the server</h5>
`./server`

<h5>Run a client</h5><p>(which simply pings the Server and gets back the external IP and port of the request)</p>
`./run Test`


<h4>Resources</h4>

<b>An nice description of how STUN works<b>

<p>http://www.vocal.com/networking/classic-stun-simple-traversal-of-udp-through-nat/</p>

<p>The presence of a Network Address Translator (NAT) presents problems for Voice over IP implementations. Consider this example using the SIP protocol. A SIP device with user Bob sits behind a NAT and wants to register its location with a SIP registrar located on the public Internet. The SIP device has a non-routable Private IP address 192.168.0.10. The SIP device registers its location with the registrar as sip:bob@192.168.0.10:5060. This tells the registrar that Bob can be reached at the IP address 192.168.0.10 at port 5060 (the default SIP port). This private IP address is meaningless to a device on the public Internet and the registrar would not know how to reach Bob. A second example involves problems in sending RTP media. Alice calls Bob and Alice’s invite contains SDP with her local IP address 10.1.1.10 and media port 1234. Bob accepts Alice’s invite with his SDP containing his local IP address 192.168.0.10 and media port 1234. Both of these IP addresses are meaningless outside the scope of each individual’s private local network and neither party will receive the other’s RTP packets.</p>
<p>Classic STUN is a client-server protocol that was created to solve some of the issues presented to VoIP implementations by NATs. Classic STUN works by discovering the presence of a NAT, the type of NAT, and the IP address/port mappings assigned by the NAT. For instance, a SIP device can use STUN to discover the external IP address of the NAT as well as the port that the NAT has assigned to it. Consider the first example from above. Before Bob registers with the SIP registrar, his SIP device would send a STUN request from its local IP address and SIP port (192.168.0.10:5060) to a STUN server located on the public Internet. The NAT is a Full Cone NAT and has a public IP address of 1.2.3.4. The NAT translates this packet and binds it to an external port 50000. The STUN server sees this packet as having been sent by 1.2.3.4:50000 and inserts this information into its response. When Bob’s SIP device receives this response it now knows that its externally mapped address is 1.2.3.4:50000. Bob can now register his location sip:bob@1.2.3.4:50000 with the registrar and the registrar will know how to reach Bob in future requests. In the second example, both Alice and Bob would perform a similar STUN transaction to learn their external NAT address and port mapping and use that address and port combination in their respective SDPs. Both Alice and Bob would then be able to communicate with each other.</p>

<b>External Resources</b>

<p>http://www.youtube.com/watch?v=9MWYw0fltr0</p>

<p>http://stackoverflow.com/questions/13501288/what-is-stun-and-does-it-need-a-port-forwarded-server</p>
<p>http://searchnetworking.techtarget.com/definition/STUN</p>
