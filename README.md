# PrimerSimulator
Quick Similator for [Primer's game](https://primerlearning.org)

This was thrown together in like two hours and was super quick for a satiation of curiosity.

Don't judge my messy code. There are lots of changes that can be made and metrics can be altered.

Game: you're presented with blob that is fair or a cheater. The blob flips a coin. If they are fair, they'll flip heads/tails 50/50.
If they are a cheater, they'll flip heads/tails 75/50.
You can make a guess as to which they are.
If you're correct, +15 flips; if you're incorrect, -30 flips.
You start with 100 flips and lose 1 every time a coin is flipped.

The Configuration class keeps track of which configurations created which results. They are then sorted. Currently, they're sorted by `winRatio`.
To change the metric, just change the implementation of `compareTo`.

The code currenly does about 14 million simulations in about 10 seconds.

Inspiration and initial data gathered by [@ThunderySpoon7](https://github.com/thunderyspoon7)
