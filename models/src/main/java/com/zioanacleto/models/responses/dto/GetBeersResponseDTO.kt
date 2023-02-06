package com.zioanacleto.models.responses.dto


import android.os.Parcelable
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonIgnoreProperties(ignoreUnknown = true)
class GetBeersResponseDTO : ArrayList<GetBeersResponseDTO.GetBeersResponseItemDTO>(),
    Parcelable {

    @Parcelize
    @JsonIgnoreProperties(ignoreUnknown = true)
    data class GetBeersResponseItemDTO(
        @JsonProperty("abv")
        var abv: Double?,
        @JsonProperty("attenuation_level")
        var attenuationLevel: Double?,
        @JsonProperty("boil_volume")
        var boilVolume: BoilVolumeDTO?,
        @JsonProperty("brewers_tips")
        var brewersTips: String?,
        @JsonProperty("contributed_by")
        var contributedBy: String?,
        @JsonProperty("description")
        var description: String?,
        @JsonProperty("ebc")
        var ebc: Double?,
        @JsonProperty("first_brewed")
        var firstBrewed: String?,
        @JsonProperty("food_pairing")
        var foodPairing: List<String?>?,
        @JsonProperty("ibu")
        var ibu: Double?,
        @JsonProperty("id")
        var id: Int?,
        @JsonProperty("image_url")
        var imageUrl: String?,
        @JsonProperty("ingredients")
        var ingredients: IngredientsDTO?,
        @JsonProperty("method")
        var method: MethodDTO?,
        @JsonProperty("name")
        var name: String?,
        @JsonProperty("ph")
        var ph: Double?,
        @JsonProperty("srm")
        var srm: Double?,
        @JsonProperty("tagline")
        var tagline: String?,
        @JsonProperty("target_fg")
        var targetFg: Double?,
        @JsonProperty("target_og")
        var targetOg: Double?,
        @JsonProperty("volume")
        var volume: VolumeDTO?
    ) : Parcelable {

        @JsonIgnoreProperties(ignoreUnknown = true)
        @Parcelize
        data class BoilVolumeDTO(
            @JsonProperty("unit")
            var unit: String?,
            @JsonProperty("value")
            var value: Int?
        ) : Parcelable

        @JsonIgnoreProperties(ignoreUnknown = true)
        @Parcelize
        data class IngredientsDTO(
            @JsonProperty("hops")
            var hops: List<HopsDTO?>?,
            @JsonProperty("malt")
            var malt: List<MaltDTO?>?,
            @JsonProperty("yeast")
            var yeast: String?
        ) : Parcelable {

            @JsonIgnoreProperties(ignoreUnknown = true)
            @Parcelize
            data class MaltDTO(
                @JsonProperty("amount")
                var amount: AmountDTO?,
                @JsonProperty("name")
                var name: String?
            ) : Parcelable {

                @JsonIgnoreProperties(ignoreUnknown = true)
                @Parcelize
                data class AmountDTO(
                    @JsonProperty("unit")
                    var unit: String?,
                    @JsonProperty("value")
                    var value: Double?
                ) : Parcelable
            }
        }

        @JsonIgnoreProperties(ignoreUnknown = true)
        @Parcelize
        data class MethodDTO(
            @JsonProperty("fermentation")
            var fermentation: FermentationDTO?,
            @JsonProperty("mash_temp")
            var mashTemp: List<MashTempDTO?>?
        ) : Parcelable {

            @JsonIgnoreProperties(ignoreUnknown = true)
            @Parcelize
            data class FermentationDTO(
                @JsonProperty("temp")
                var temp: TempDTO?
            ) : Parcelable {

                @JsonIgnoreProperties(ignoreUnknown = true)
                @Parcelize
                data class TempDTO(
                    @JsonProperty("unit")
                    var unit: String?,
                    @JsonProperty("value")
                    var value: Double?
                ) : Parcelable
            }

            @JsonIgnoreProperties(ignoreUnknown = true)
            @Parcelize
            data class MashTempDTO(
                @JsonProperty("duration")
                var duration: Int?,
                @JsonProperty("temp")
                var temp: TempDTO?
            ) : Parcelable {

                @JsonIgnoreProperties(ignoreUnknown = true)
                @Parcelize
                data class TempDTO(
                    @JsonProperty("unit")
                    var unit: String?,
                    @JsonProperty("value")
                    var value: Int?
                ) : Parcelable
            }
        }

        @JsonIgnoreProperties(ignoreUnknown = true)
        @Parcelize
        data class VolumeDTO(
            @JsonProperty("unit")
            var unit: String?,
            @JsonProperty("value")
            var value: Int?
        ) : Parcelable

        @JsonIgnoreProperties(ignoreUnknown = true)
        @Parcelize
        data class HopsDTO(
            @JsonProperty("name") var name: String?,
            @JsonProperty("amount") var amount: IngredientsDTO.MaltDTO.AmountDTO?
        ) : Parcelable
    }
}