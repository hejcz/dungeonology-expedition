package io.github.hejcz.dungeonology.game.card

inline class AuxiliaryEffectId(val id: Int)

sealed class CardInProgress

class SomeCardInProgress(
    val effect: CardEffect,
    val stage: Int
) : CardInProgress()

object NoCardInProgress : CardInProgress()

interface Card {
    val type: CardType
    val power: Power
    val mainEffect: CardEffect
    val auxiliaryEffects: Map<AuxiliaryEffectId, CardEffect>
}

data class Card4(
    override val type: CardType = CardType.FATE,
    override val power: Power = Power(4),
    override val mainEffect: CardEffect = NoEffect,
    override val auxiliaryEffects: Map<AuxiliaryEffectId, CardEffect> = mapOf(
        AuxiliaryEffectId(1) to IncreaseStealthEffect(change = 4, studentsToDraw = 1),
        AuxiliaryEffectId(2) to IncreaseAlertEffect(change = 4, studentsToDraw = 2)
    )
) : Card

data class LovePotion(
    override val type: CardType = CardType.MAGIC,
    override val power: Power = Power(2),
    override val mainEffect: CardEffect = LovePotionMainEffect,
    override val auxiliaryEffects: Map<AuxiliaryEffectId, CardEffect> = mapOf(
        AuxiliaryEffectId(1) to LovePotionAuxiliaryEffect
    )
) : Card

data class BackStab(
    override val type: CardType = CardType.SUBTERFUGE,
    override val power: Power = Power(3),
    override val mainEffect: CardEffect = BackStabMainEffect,
    override val auxiliaryEffects: Map<AuxiliaryEffectId, CardEffect> = mapOf(
        AuxiliaryEffectId(1) to IncreaseStealthEffect(change = 3),
        AuxiliaryEffectId(2) to IncreaseAlertEffect(change = 3, studentsToDraw = 1)
    )
) : Card

